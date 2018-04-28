package com.zelidre.spring5recipeapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zelidre.spring5recipeapp.domain.Recipe;
import com.zelidre.spring5recipeapp.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	final RecipeRepository recipeRepository;
	
	

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}



	@Override
	@Transactional
	public void saveImageFile(Long id, MultipartFile file) {
		try {
			Recipe recipe = recipeRepository.findById(id).get();
			Byte[] byteObject = new Byte[file.getBytes().length];
			
			
			int i=0;
			
			for (byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
			recipe.setImage(byteObject);
			
			recipeRepository.save(recipe);
		} catch (IOException e) {
			log.error("Error Occurd : ", e);
			
			e.printStackTrace();
			
		}
		
	}

}
