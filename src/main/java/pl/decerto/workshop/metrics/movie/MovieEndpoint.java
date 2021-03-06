package pl.decerto.workshop.metrics.movie;

import com.codahale.metrics.annotation.Timed;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movie")
class MovieEndpoint {

	private final MovieRepository movieRepository;

	private final MovieSaver movieSaver;

	public MovieEndpoint(MovieRepository movieRepository, MovieSaver movieSaver) {
		this.movieRepository = movieRepository;
		this.movieSaver = movieSaver;
	}

	@Timed(name = "movie.get", absolute = true)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<Movie> getMovie() {
		return movieRepository.findAll();
	}

	@Timed(name = "movie.add", absolute = true)
	@RequestMapping(method = RequestMethod.POST, path = "/{name}")
	void add(@PathVariable(name = "name") String name) {
		movieSaver.save(new Movie(name));
	}

}
