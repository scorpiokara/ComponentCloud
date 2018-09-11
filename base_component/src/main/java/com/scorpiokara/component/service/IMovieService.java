package com.scorpiokara.component.service;

/**
 * Created by .
 * 接口中定义了 组件向外提供的数据传递的接口方法
 * @author TaoYJ
 * @date 2018/8/13
 */
public interface IMovieService {


    /**
     * 获取视频名称
     */
    public String getMovieName();

    public class EmptyMovieService implements IMovieService {

        @Override
        public String getMovieName() {
            return "";
        }
    }
}
