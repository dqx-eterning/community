package dqx.community.mapper;

import dqx.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
