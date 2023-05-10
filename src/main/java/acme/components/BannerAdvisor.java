
package acme.components;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entitites.banner.Banner;
import acme.framework.helpers.MomentHelper;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected BannerAdvisorRepository repo;


	@ModelAttribute("banner")
	protected Banner getRandomBannerFromPresent() {
		Date present;
		List<Banner> banners;
		Banner result;
		int i;
		Random rand;

		present = MomentHelper.getCurrentMoment();
		banners = this.repo.findBannerFromPresent(present).stream().collect(Collectors.toList());

		rand = new Random();
		i = rand.nextInt(banners.size());

		result = banners.get(i);

		return result;
	}
}
