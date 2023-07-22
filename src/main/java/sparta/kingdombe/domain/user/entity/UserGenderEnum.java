package sparta.kingdombe.domain.user.entity;

public enum UserGenderEnum {
    MALE("Male"),
    FEMALE("Female");
    private final String gender;

    UserGenderEnum(String gender){
        this.gender = gender;
    }

    public String toStringGender(){
        return this.gender;
    }

}
