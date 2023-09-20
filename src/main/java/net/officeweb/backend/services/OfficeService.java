package net.officeweb.backend.services;

import net.officeweb.backend.entities.OfficeEntity;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

        @Autowired
        private final OfficeRepository officeRepository;

        public OfficeService(OfficeRepository officeRepository) {

            this.officeRepository = officeRepository;
        }
        public List<OfficeEntity> findAllOffice() {

            return officeRepository.findAll();
        }
        public Optional<OfficeEntity> findById(Long id) {

            return officeRepository.findById(id);
        }
        public OfficeEntity saveOffice(OfficeEntity officeEntity) {

            return officeRepository.save(officeEntity);
        }
        public OfficeEntity updateOffice(Long id, OfficeEntity officeEntity) {

            OfficeEntity office = officeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

            office.setTitle(officeEntity.getTitle());
            office.setAddress(officeEntity.getAddress());
            office.setFloor(officeEntity.getFloor());
            office.setParking(officeEntity.getParking());
            office.setPrice(officeEntity.getPrice());
            office.setYear(officeEntity.getYear());
            office.setSpace(officeEntity.getSpace());
            office.setDescription((officeEntity.getDescription()));
            office.setTelephone(officeEntity.getTelephone());
            office.setCity(officeEntity.getCity());
            office.setImages(officeEntity.getImages());
            //////////
            office.setAvailableFrom(officeEntity.getAvailableFrom());
            office.setAvailableTo(officeEntity.getAvailableTo());

            OfficeEntity updatedOffice = officeRepository.save(office);
            return updatedOffice;
        }

//        public List<OfficeEntity> getAvailable(String dateFrom, String dateTo) {
//            return officeRepository.findAllBetweenDates(dateFrom, dateTo);
//        }

        //////////////

        public void uploadImage(Long id, MultipartFile multipartFile) throws IOException {
            OfficeEntity office = officeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Office doesn't exist with id :" + id));

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            office.setImages(fileName);
            officeRepository.save(office);
            String uploadDir = "office-images/" + id;
            FileUpload.saveFile(uploadDir, fileName, multipartFile);

        }
        public void deleteOffice(Long id) {
            officeRepository.deleteById(id);
        }

        public void deleteOfficeByAccountId(Long id) {
            officeRepository.deleteByCustomerAccountId(id);
        }

        public List<OfficeEntity> findByCustomerAccountId(Long id){
            return officeRepository.findByCustomerAccountId(id);
        }
        public String OfficeAccountId(Long officeId){
            OfficeEntity office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Office not exist with id :" + officeId));
            return office.getCustomerAccount().getUsername();
        }
    }

