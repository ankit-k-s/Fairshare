package com.fairshare.fairshare.web;

import com.fairshare.fairshare.domain.Group;
import com.fairshare.fairshare.domain.GroupMember;
import com.fairshare.fairshare.domain.User;
import com.fairshare.fairshare.repo.GroupMemberRepository;
import com.fairshare.fairshare.repo.GroupRepository;
import com.fairshare.fairshare.repo.UserRepository;
import com.fairshare.fairshare.web.dto.CreateGroupRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupRepository groupRepo;
    private final UserRepository userRepo;
    private final GroupMemberRepository gmRepo;

    public GroupController(GroupRepository groupRepo, UserRepository userRepo, GroupMemberRepository gmRepo) {
        this.groupRepo = groupRepo; this.userRepo = userRepo; this.gmRepo = gmRepo;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Group> create(@RequestBody @Valid CreateGroupRequest req){
        Group g = groupRepo.save(new Group(req.getName()));
        for (Long uid : req.getMemberUserIds()) {
            User u = userRepo.findById(uid).orElseThrow();
            gmRepo.save(new GroupMember(g, u));
        }
        return ResponseEntity.created(URI.create("/api/groups/" + g.getId())).body(g);
    }

    @GetMapping("/{id}/members")
    public List<GroupMember> members(@PathVariable Long id){
        return gmRepo.findByGroupId(id);
    }
}
