package com.bhagyashri.codebproject.controller;

import com.bhagyashri.codebproject.dto.GroupRequest;
import com.bhagyashri.codebproject.dto.GroupResponse;
import com.bhagyashri.codebproject.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        try {
            List<GroupResponse> groups = groupService.getAllGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest request) {
        try {
            if (request.getGroupName() == null || request.getGroupName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Group name cannot be empty");
            }
            GroupResponse response = groupService.createGroup(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the group");
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(
            @PathVariable Long groupId,
            @RequestBody GroupRequest request) {
        try {
            if (request.getGroupName() == null || request.getGroupName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Group name cannot be empty");
            }
            GroupResponse response = groupService.updateGroup(groupId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the group");
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        try {
            groupService.deleteGroup(groupId);
            return ResponseEntity.ok().body("Group deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the group");
        }
    }
} 