package com.waltercross.geneatoolapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.waltercross.geneatoolcore.RegistryRecord;
import com.waltercross.geneatoolcore.RegistryRecordRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class RecordController {

	private final RegistryRecordRepository repository;

	public RecordController(RegistryRecordRepository repository) {
		super();
		this.repository = repository;
	}
	
	// Aggregate root.
	
		@GetMapping("/records")
		List<RecordDTO> all() {
			
			List<RecordDTO> result = new ArrayList<RecordDTO>();			
			repository.findAll().forEach(r -> result.add(RecordDTO.convert(r)));
			
			return result;
		}
		
		@PostMapping("/records")
		RecordDTO newRegistryRecord(@RequestBody RecordDTO newRegistryRecord) {
			RegistryRecord r = repository.save(RecordDTO.convert(newRegistryRecord));
			return RecordDTO.convert(r);
		}
		
		// Single item.
		@GetMapping("/records/{id}")
		RecordDTO one(@PathVariable String id) {
			RegistryRecord r = repository.findById(id)
					.orElseThrow(() -> new RegistryRecordNotFoundException(id));
			
			return RecordDTO.convert(r);
		}
		
		@PutMapping("/records/{id}")
		RecordDTO replaceRegistryRecord(@RequestBody RecordDTO newRegistryRecord, @PathVariable String id) {
			RegistryRecord newRecord = RecordDTO.convert(newRegistryRecord);
			return repository.findById(id)
					.map(record -> {
						
						record.folio = newRecord.folio;
						record.recordDate = newRecord.recordDate;
						record.recordType = newRecord.recordType;
						record.registryBook = newRecord.registryBook;
						record.location = newRecord.location;
						record.people = newRecord.people;
						
						RegistryRecord r = repository.save(record);
						
						return RecordDTO.convert(r);
					})
					.orElseGet(() -> {
						newRecord.id = id;
						
						RegistryRecord r = repository.save(newRecord);
						
						return RecordDTO.convert(r);
					});
		}
		
		@DeleteMapping("records/{id}")
		void deleteRegistryRecord(@PathVariable String id) {
			repository.deleteById(id);
		}
}
