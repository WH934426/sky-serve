package com.wh.controller.admin;

import com.wh.service.WorkspaceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作台控制器
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
public class WorkspaceController {

    @Resource
    private WorkspaceService workspaceService;
}
