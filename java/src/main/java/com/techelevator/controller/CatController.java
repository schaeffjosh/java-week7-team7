package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard getRandom() {
        CatCard newCatCard = new CatCard();
        newCatCard.setCatFact(catFactService.getFact().getText());
        newCatCard.setImgUrl(catPicService.getPic().getFile());
        return newCatCard;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CatCard get(@PathVariable int id){
        return catCardDao.getCatCardById(id);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CatCard> listAllCatCards() {
        return catCardDao.getCatCards();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CatCard addCatCard(@Valid @RequestBody CatCard catCard){
        return catCardDao.createCatCard(catCard);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CatCard updateCatCard(@Valid @RequestBody CatCard catCard, @PathVariable int id){
        catCard.setCatCardId(id);
        try {
            CatCard updatedCatCard = catCardDao.updateCatCard(catCard);
            return updatedCatCard;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CatCard not found.");
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }
}
