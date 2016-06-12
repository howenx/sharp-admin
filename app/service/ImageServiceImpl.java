package service;

import domain.Image;
import mapper.ImageMapper;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/6/8.
 */
public class ImageServiceImpl implements ImageService {

    @Inject
    private ImageMapper imageMapper;

    /**
     * 上传优惠券图片     Added by Tiffany Zhu 2016.06.08
     * @param image
     */
    @Override
    public void addImage(Image image) {
        imageMapper.addImage(image);
    }
}

