package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.Message;
import com.coderscampus.assignment14.dto.MessageDTO;
import com.coderscampus.assignment14.service.MessageService;
import com.coderscampus.assignment14.web.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/channel/{channelId}/messages")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessages(
            @PathVariable Integer channelId,
            @RequestParam(defaultValue = "-1") Integer after) {
        List<MessageDTO> messages = messageService.getMessagesForChannel(channelId, after)
            .stream()
            .map(messageService::convertToDTO)
            .toList();    
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Messages retrieved successfully", messages));
    }

    @PostMapping("/channel/{channelId}/messages")
    public ResponseEntity<ApiResponse<MessageDTO>> createMessage(
            @PathVariable Integer channelId,
            @RequestBody MessageDTO messageDTO) {
        Message message = messageService.createMessage(channelId, messageDTO.getUsername(), messageDTO.getContent());
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Message created successfully", messageService.convertToDTO(message)));
    }
}