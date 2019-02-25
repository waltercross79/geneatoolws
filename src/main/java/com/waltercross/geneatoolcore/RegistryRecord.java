package com.waltercross.geneatoolcore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;

public class RegistryRecord {
    @Id
    public String id;
    public Date recordDate;
    public String folio;
    public Location location;
    public String registryBook;
    public RecordType recordType;
    public List<PersonInRecord> people;
    public String fileName;

    /**
     * Default constructor for reflection and MongoDB repository.
     */
    public RegistryRecord() {

    }

    /**
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param recordType
     * @param registryBook
     * @param fileName
     */
    public RegistryRecord(Date recordDate, String folio, String houseNumber,
                          String streetName, String city, String country,
                          RecordType recordType, String registryBook,
                          List<PersonInRecord> people, String fileName) {

        this(recordDate, folio, houseNumber, streetName, city,
                country, recordType, registryBook, people, null, fileName);
    }

    /**
     * @param recordDate
     * @param folio
     * @param houseNumber
     * @param streetName
     * @param city
     * @param country
     * @param recordType
     * @param registryBook
     * @param id
     * @param fileName
     */
    public RegistryRecord(Date recordDate, String folio, String houseNumber,
                          String streetName, String city, String country,
                          RecordType recordType, String registryBook,
                          List<PersonInRecord> people,
                          String id, String fileName) {

        this.recordDate = recordDate;
        this.folio = folio;
        this.location = new Location(country, streetName, houseNumber, city);
        this.id = id;
        this.recordType = recordType;
        this.registryBook = registryBook;
        this.people = people;
        this.fileName = fileName;
    }
}