package cerberus.party;

import java.time.LocalDate;

public class Person extends Contact {
    private LocalDate birthDate;

    public Person(String name, LocalDate birthDate, int mobile, String email) {
        super(name, mobile, email);
        this.birthDate = birthDate;
    }

    public static Person empty() {
        return new Person("", LocalDate.now(), -1, "");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return LocalDate.now().getYear() - this.getBirthDate().getYear();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
