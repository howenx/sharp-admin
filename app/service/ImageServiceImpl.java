package service;

import domain.Image;
import mapper.ImageMapper;

/**
 * Created by tiffany on 16/6/8.
 */
public class ImageServiceImpl implements ImageService {

//    @Inject   //注释 products 和 coupon库做的修改
    private ImageMapper imageMapper;


    //--------注释 products 和 coupon库做的修改-------     Modified By Sunny Wu 2016.09.01
    @Override
    public void addImage(Image image) {
    }
    //--------注释 products 和 coupon库做的修改-------



//    /**
//     * 上传优惠券图片     Added by Tiffany Zhu 2016.06.08
//     * @param image
//     */
//    @Override
//    public void addImage(Image image) {
//        imageMapper.addImage(image);
//    }
}

