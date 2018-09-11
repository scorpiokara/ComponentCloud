package com.scorpiokara.cloud.movie;

import com.scorpiokara.component.service.IMovieService;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/13
 */
public class MovieService implements IMovieService {

    @Override
    public String getMovieName() {
        return "一出好戏";
    }
}
