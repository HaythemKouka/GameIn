package GameIn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import GameIn.model.dto.GameDTO;
import GameIn.model.services.IPlayerGamerService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping
public class RestController {

    private IPlayerGamerService services;

    @Autowired
    public RestController(IPlayerGamerService services) {
        this.services = services;
    }

    @GetMapping("/paginable")
    public List<GameDTO> getPage(
            @RequestParam("id") int id,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize
    ){
        return services.getGamePagination(id, pageNo, pageSize);
    }



}
