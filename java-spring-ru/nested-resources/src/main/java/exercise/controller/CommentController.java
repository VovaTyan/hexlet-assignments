package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getPostComments(@PathVariable Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getComment(@PathVariable long postId, @PathVariable long commentId) {

        return commentRepository.findByIdAndPostId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }
    @PostMapping(path = "/{postId}/comments")
    public Iterable<Comment> createPostComments(@PathVariable Long postId, @RequestBody Post post) {
        post.setComments();
        postRepository.save(post);
        return commentRepository.findAllByPostId(postId);
    }


    // END
}
