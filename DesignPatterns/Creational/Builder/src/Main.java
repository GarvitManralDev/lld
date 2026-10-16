// Builder pattern demo

void main() {

    Person p1 = new Person.Builder("Garvit",23)
                .addGender("Male")
                .addRace("Asian")
                .build();

    p1.printDetails();
}
