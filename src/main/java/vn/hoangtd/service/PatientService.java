package vn.hoangtd.service;

/**
 * Created by hoangtd on 1/23/2017.
 */
public interface PatientService {
    List<Patient> findAll();

    Patient findById(long id);
}
