package com.study.oauth2.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.oauth2.dto.posts.RegisterPostsReqDto;
import com.study.oauth2.entity.Posts;
import com.study.oauth2.entity.PostsImg;
import com.study.oauth2.repository.PostsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsService {
	@Value("${file.path}")
	private String filePath;

	private final PostsRepository postsRepository;

	public int registerPost(RegisterPostsReqDto registerPostsReqDto) {

		Posts posts = registerPostsReqDto.toEntity();
		postsRepository.registerPosts(posts);

		uploadFile(posts.getPostsId(),registerPostsReqDto.getImgFiles());
		return postsRepository.registerPostsImgs(uploadFile(posts.getPostsId(), registerPostsReqDto.getImgFiles()));

	}

	private List<PostsImg> uploadFile(int postsId, List<MultipartFile> files) {
		if (files == null) {
			return null;

		}
		List<PostsImg> postsFiles = new ArrayList<>();
		files.forEach(file -> {
			String originFileName = file.getOriginalFilename();
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

			Path uploadPath = Paths.get(filePath + "post/" + tempFileName);

			File f = new File(filePath + "post");
			if (!f.exists()) { // 파일경로가 존재하지 않으면
				f.mkdirs(); // 전체하위 경로(없는것까지) 만들어줌
			}
			try {
				Files.write(uploadPath, file.getBytes());
			} catch (IOException e) {

				e.printStackTrace();
			}

			postsFiles.add(PostsImg.builder().postId(postsId).originName(originFileName).tempName(tempFileName)
					.ImgSize(Long.toString(file.getSize())).build());

		});
		return postsFiles;

	}

}
