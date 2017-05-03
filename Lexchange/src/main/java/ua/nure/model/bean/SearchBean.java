package ua.nure.model.bean;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ua.nure.model.AppUser;
import ua.nure.service.AppUserService;
import org.joda.time.LocalDate;

import java.util.*;

public class SearchBean {

    private static final int PAGE_SIZE = 12;

    private AppUserService service;

    private String nativeLanguage;

    private String country;

    private String interestedIn;

    private String age;

    private List<String> interests;

    private AppUser currentUser;

    private List<AppUser> allUsers;

    private int currentPage;

    private boolean needToReload;

    private String stringCriteria;

    public SearchBean() {
        this.interests = Arrays.asList("religion", "sport", "music", "games",
                "politics", "trips", "art", "science");
        this.nativeLanguage = "not";
        this.country = "not";
        this.interestedIn = "not";
        this.age = "not";
        this.stringCriteria="";
        this.currentPage = 1;
    }

    public Criteria buildCriteria(Criteria criteria) {
        if(!nativeLanguage.equals("not")) {
            criteria.add(Restrictions.eq("nativeLanguage", nativeLanguage));
        }
        if(!country.equals("not")) {
            criteria.add(Restrictions.eq("country", country));
        }
        if(!interestedIn.equals("not")) {
            criteria.add(Restrictions.ilike("interestedIn", interestedIn, MatchMode.ANYWHERE));
        }
        if(!age.equals("not")) {
            addAgeCriteria(criteria);
        }
        if(!stringCriteria.isEmpty()) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("firstName", stringCriteria, MatchMode.ANYWHERE),
                    Restrictions.ilike("email", stringCriteria, MatchMode.ANYWHERE),
                    Restrictions.ilike("lastName", stringCriteria, MatchMode.ANYWHERE)));
        }
        return criteria;
    }

    private void addAgeCriteria (Criteria criteria) {
        LocalDate date = new LocalDate();
        switch (age) {
            case "small":
                criteria.add(Restrictions.between("birthDate", date.minusYears(12), date));
                break;
            case "teen":
                criteria.add(Restrictions.between("birthDate", date.minusYears(17), date.minusYears(13)));
                break;
            case "yang":
                criteria.add(Restrictions.between("birthDate", date.minusYears(29), date.minusYears(18)));
                break;
            case "adult":
                criteria.add(Restrictions.between("birthDate", date.minusYears(59), date.minusYears(30)));
                break;
            case "old":
                criteria.add(Restrictions.between("birthDate", date.minusYears(100), date.minusYears(60)));
                break;
        }
    }

    private Comparator<AppUser> buildComparator() {
        return (o1, o2) -> {
            int comparing = 0;
            if (interests.contains("religion"))
                comparing += (Math.abs(currentUser.getReligion() - o1.getReligion())
                            - Math.abs(currentUser.getReligion() - o2.getReligion()));
            if (interests.contains("sport"))
                comparing += (Math.abs(currentUser.getSport() - o1.getSport())
                        - Math.abs(currentUser.getSport() - o2.getSport()));
            if (interests.contains("music"))
                comparing += (Math.abs(currentUser.getMusic() - o1.getMusic())
                        - Math.abs(currentUser.getMusic() - o2.getMusic()));
            if (interests.contains("games"))
                comparing += (Math.abs(currentUser.getGames() - o1.getGames())
                        - Math.abs(currentUser.getGames() - o2.getGames()));
            if (interests.contains("politics"))
                comparing += (Math.abs(currentUser.getPolitics() - o1.getPolitics())
                        - Math.abs(currentUser.getPolitics() - o2.getPolitics()));
            if (interests.contains("trips"))
                comparing += (Math.abs(currentUser.getTrips() - o1.getTrips())
                        - Math.abs(currentUser.getTrips() - o2.getTrips()));
            if (interests.contains("art"))
                comparing += (Math.abs(currentUser.getArt() - o1.getArt())
                        - Math.abs(currentUser.getArt() - o2.getArt()));
            if (interests.contains("science"))
                comparing += (Math.abs(currentUser.getScience() - o1.getScience())
                        - Math.abs(currentUser.getScience() - o2.getScience()));
            return comparing * 1000 + o2.getFirstName().compareTo(o1.getFirstName())
                    + o2.getLastName().compareTo(o1.getLastName())
                    + o2.getCountry().compareTo(o1.getCountry())
                    + o2.getNativeLanguage().compareTo(o1.getNativeLanguage())
                    + o2.getEmail().compareTo(o1.getEmail())
                    + o2.getRole().name().compareTo(o1.getRole().name())
                    + (int) (o2.getId() - o1.getId());
        };
    }

    public String getRandomColor() {
        Random random = new Random();
        int nextInt = random.nextInt(256*256*256);
        return String.format("#%06x", nextInt);
    }

    public AppUserService getService() {
        return service;
    }

    public void setService(AppUserService service) {
        this.service = service;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
        needToReload = true;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        needToReload = true;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
        needToReload = true;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        needToReload = true;
    }

    public List<AppUser> getAllUsers() {
        if(needToReload || allUsers == null) {
            allUsers = new ArrayList<>();
            allUsers.addAll(service.findByCriteria(this));
            allUsers.remove(currentUser);
            needToReload = false;
        }
        return allUsers;
    }

    public void setAllUsers(List<AppUser> allUsers) {
        this.allUsers = allUsers;
    }

    public List<Integer> getPages() {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= getAllUsers().size() / PAGE_SIZE; i++) {
            if (currentPage - i < 3 && i - currentPage < 3) {
                list.add(i);
            }
        }
        return list;
    }

    public List<AppUser> getPageUsers() {
        List<AppUser> sorted = new ArrayList<>();
        sorted.addAll(getAllUsers());
        sorted.sort(buildComparator());
        if(getCurrentPage() * PAGE_SIZE < sorted.size()) {
            return sorted.subList((getCurrentPage() - 1) * PAGE_SIZE, getCurrentPage() * PAGE_SIZE);
        } else if ((getCurrentPage() - 1) * PAGE_SIZE < sorted.size()) {
            return sorted.subList((getCurrentPage() - 1) * PAGE_SIZE, sorted.size());
        } else {
            return new ArrayList<>();
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 1) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getStringCriteria() {
        return stringCriteria;
    }

    public void setStringCriteria(String stringCriteria) {
        this.stringCriteria = stringCriteria;
    }
}
