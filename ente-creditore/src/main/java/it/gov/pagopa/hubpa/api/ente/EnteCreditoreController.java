package it.gov.pagopa.hubpa.api.ente;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnteCreditoreController {

  @Autowired
  private EnteCreditoreService enteCreditoreService;

  @Autowired
  private ModelMapper modelMapper;

  Logger logger = LoggerFactory.getLogger(EnteCreditoreController.class);

  @GetMapping(value = "/ente/{codiceFiscaleRefP}")
  public EnteCreditoreMinimalDto getEnteCreditoreByRefP(@PathVariable("codiceFiscaleRefP") String codiceFiscaleRefP) {
    logger.debug("xxxxxxxxxxxxxx");
    return convertToDto(enteCreditoreService.getEnteCreditoreByRefP(codiceFiscaleRefP));
  }

  private EnteCreditoreMinimalDto convertToDto(EnteCreditoreEntity ecEntity) {
    return modelMapper.map(ecEntity, EnteCreditoreMinimalDto.class);
  }

  /*
   * private Post convertToEntity(PostDto postDto) throws ParseException { Post
   * post = modelMapper.map(postDto, Post.class); post.setSubmissionDate(
   * postDto.getSubmissionDateConverted(userService.getCurrentUser().getPreference
   * ().getTimezone()));
   * 
   * if (postDto.getId() != null) { Post oldPost =
   * postService.getPostById(postDto.getId());
   * post.setRedditID(oldPost.getRedditID()); post.setSent(oldPost.isSent()); }
   * return post; }
   */

}