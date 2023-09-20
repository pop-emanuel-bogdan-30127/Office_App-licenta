package net.officeweb.backend.controllers;

import net.officeweb.backend.entities.CustomerAccount;
import net.officeweb.backend.entities.OfficeEntity;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.services.CustomerAccountService;
import net.officeweb.backend.services.FileUpload;
import net.officeweb.backend.services.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

        @Autowired
        private final OfficeService officeService;

        @Autowired
        private final CustomerAccountService customerAccountService;

        public OfficeController(OfficeService officeService, CustomerAccountService customerAccountService) {
            this.officeService = officeService;
            this.customerAccountService = customerAccountService;
        }

        @GetMapping
        public List<OfficeEntity> findAllOffice(){
            return officeService.findAllOffice();
        }

        @PostMapping
            public OfficeEntity saveOffice(@RequestBody OfficeEntity officeEntity){
            return officeService.saveOffice(officeEntity);
        }

        @GetMapping("/{officeId}")
        public Optional<OfficeEntity> findOfficeById(@PathVariable("officeId") Long officeId){
            return officeService.findById(officeId);
        }

        @PutMapping("/{officeId}/image")
        public OfficeEntity updateOfficeWithImage(@PathVariable Long officeId, @RequestBody OfficeEntity officeEntity, @RequestParam("image") MultipartFile multipartFile)throws IOException{

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            officeEntity.setImages(fileName);

            OfficeEntity office = officeService.updateOffice(officeId, officeEntity);
            String uploadDir = "user-photos/" + office.getId();
            FileUpload.saveFile(uploadDir, fileName, multipartFile);

            return officeService.updateOffice(officeId, officeEntity);
        }
        @PutMapping("/{officeId}")
        public OfficeEntity updateOffice(@PathVariable Long officeId,
                                         @RequestBody OfficeEntity officeEntity){
            return officeService.updateOffice(officeId, officeEntity);
        }

        @DeleteMapping("/{officeId}")
        public void deleteOffice(@PathVariable("officeId") Long officeId){
            officeService.deleteOffice(officeId);
        }

        @GetMapping("/{accountId}/offices")
        public Optional<List<OfficeEntity>> getAllOfficesByAccountId(@PathVariable("accountId") Long accountId){
            Optional<List<OfficeEntity>> offices = Optional.ofNullable(officeService.findByCustomerAccountId(accountId));
            return offices;
        }
        @PostMapping("/{accountId}/image-office")
        public OfficeEntity createOffice(@PathVariable("accountId") Long accountId, @RequestBody OfficeEntity officeEntity,
                                     @RequestParam("image") MultipartFile multipartFile)throws IOException {
            Optional<Object> office = Optional.ofNullable(customerAccountService.findById(accountId).map(customerAccount -> {
                officeEntity.setCustomerAccount(customerAccount);
                OfficeEntity savedOffice =  officeService.saveOffice(officeEntity);
                try {
                    officeService.uploadImage(savedOffice.getId(), multipartFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return officeService.findById(savedOffice.getId());
            }).orElseThrow(() -> new ResourceNotFoundException("CustomerAccount not found for id = " + accountId)));
            return officeEntity;
        }

        @PostMapping("/{officeId}/image")
        public void uploadImage(@PathVariable("officeId") Long officeId, @RequestParam("image") MultipartFile multipartFile)throws IOException{
            officeService.uploadImage(officeId, multipartFile);
        }

        @PostMapping("/{accountId}/offices")
        public OfficeEntity createOffice(@PathVariable("accountId") Long accountId, @RequestBody OfficeEntity officeEntity)throws IOException {
            Optional<Object> office = Optional.ofNullable(customerAccountService.findById(accountId).map(customerAccount -> {
                officeEntity.setCustomerAccount(customerAccount);
                return officeService.saveOffice(officeEntity);
            }).orElseThrow(() -> new ResourceNotFoundException("CustomerAccount not found for id = " + accountId)));
            return officeEntity;
        }

        @DeleteMapping("/{accountId}/offices")
        public void deleteAllOfficesOfAccount(@PathVariable("accountId") Long accountId) {
            officeService.deleteOfficeByAccountId(accountId);
        }

        @GetMapping("/{accountId}/account")
        public String getOfficeAccountId(@PathVariable("accountId") Long accountId){
            return officeService.OfficeAccountId(accountId);
        }

        @GetMapping("/{officeId}/get-image-path")
        public String getImagePath(@PathVariable("officeId") Long id){
            return (officeService.findById(id)).get().getPhotosImagePath();
        }

    }
