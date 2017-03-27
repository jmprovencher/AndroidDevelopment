package ca.ulaval.ima.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String firstName;
    private String lastName;
    private String birthday;
    private String department;
    private String sex;

    // Constructor
    public Student(String firstName, String LastName, String birthday, String department, String sex) {
        this.firstName = firstName;
        this.lastName = LastName;
        this.birthday = birthday;
        this.department = department;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDepartment() {
        return department;
    }

    public String getSex() {
        return sex;
    }


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.firstName,
                this.lastName,
                this.birthday,
                this.department,
                this.sex});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    // Parcelling part
    public Student(Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.firstName = data[0];
        this.lastName = data[1];
        this.birthday = data[2];
        this.department = data[3];
        this.sex = data[4];
    }
}