public class Person {
    private final String name;
    private final int age;
    private final String race;
    private final String gender;


    public Person(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.race = builder.race;
        this.gender = builder.gender;
    }

    public static class Builder{
        private  String name;
        private  int age;
        private  String race = null;
        private  String gender = null;

        public Builder (String name, int age){
            this.name = name;
            this.age = age;
        }

        public Builder addRace(String race){
            this.race = race;
            return this;
        }

        public Builder addGender(String gender){
            this.gender = gender;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }


    public void printDetails(){
        System.out.println("Name - "+ this.name);
        System.out.println("Age - "+ this.age);
        System.out.println("Race - "+ this.race);
        System.out.println("Gender - "+ this.gender);
    }
}
