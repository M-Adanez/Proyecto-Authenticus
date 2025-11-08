package es.deusto.sd.proyecto.Controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO; // Add this import
import es.deusto.sd.proyecto.Service.CaseInvestigationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cases")
@Tag(name = "Case Investigation", description = "CRUD operations for Case Investigation")
public class caseInvestigationController {

    private final CaseInvestigationService ciService;

    public caseInvestigationController(CaseInvestigationService ciService){
        this.ciService = ciService;
    }

    // Create Case Investigation
    @Operation(summary = "Create a new Case Investigation")
    @PostMapping("/{token}")
    public ResponseEntity<String> createCase(@PathVariable UUID token, @RequestBody CaseInvestigation ci){
        // Convert CaseInvestigation to CaseInvestigationDTO
        CaseInvestigationDTO ciDTO = ciService.CI_to_DTO(ci);
        ciService.createCaseInvestigation(token, ciDTO);
        return new ResponseEntity<>("Case created successfully", HttpStatus.CREATED);
    }

    // Get last 5 cases
    @Operation(summary = "Get the last 5 Case Investigations")
    @GetMapping("/{token}/last")
    public ResponseEntity<List<CaseInvestigation>> getLast5(@PathVariable UUID token){
        return ResponseEntity.ok(ciService.getCaseInvestigations(token));
    }

    // Get last N cases
    @Operation(summary = "Get the last N Case Investigations")
    @GetMapping("/{token}/last/{n}")
    public ResponseEntity<List<CaseInvestigation>> getLastN(@PathVariable UUID token, @PathVariable int n){
        return ResponseEntity.ok(ciService.getCaseInvestigationsN(token, n));
    }

    // Get cases by date range
    @Operation(summary = "Get Case Investigations by date range")
    @GetMapping("/{token}/dates")
    public ResponseEntity<List<CaseInvestigation>> getByDate(
        @PathVariable UUID token,
        @RequestParam Date startDate,
        @RequestParam Date endDate){

        return ResponseEntity.ok(ciService.getCaseInvestigationsInDate(token, startDate, endDate));
    }

    // Delete Case by ID
    @Operation(summary = "Delete a Case Investigation by ID")
    @DeleteMapping("/{token}/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID token, @PathVariable int id){
        ciService.deleteCaseInvestigation(token, id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // Add files to case
    @Operation(summary = "Add files to a case")
    @PutMapping("/{token}/{id}/files")
    public ResponseEntity<String> addFiles(
        @PathVariable UUID token,
        @PathVariable int id,
        @RequestBody List<String> filesURL){
        
        ciService.addFilesToCase(token, filesURL, id);
        return ResponseEntity.ok("Files added successfully");
    }
}