/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.lecture.LectureType;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

	@Query("select count(l) from Lecture l where l.lectureType = :type and l.lecturer.id = :id")
	Integer findLecturesByType(LectureType type, int id);

	@Query("select avg(l.estimateLearningTime) from Lecture l where l.lecturer.id = :id")
	Double getAverageLearningTimeLectures(int id);
	@Query("select stddev(l.estimateLearningTime) from Lecture l where l.lecturer.id = :id")
	Double getDesviationLearningTimeLectures(int id);
	@Query("select max(l.estimateLearningTime) from Lecture l where l.lecturer.id = :id")
	Double getMaxLearningTimeLectures(int id);
	@Query("select min(l.estimateLearningTime) from Lecture l where l.lecturer.id = :id")
	Double getMinLearningTimeLectures(int id);

	@Query(value = "select avg(plus) from (select sum(l.estimate_learning_time) as plus from lecture l INNER JOIN course_lecture cl ON cl.lecture_id = l.id where l.lecturer_id = :id GROUP BY cl.course_id) as r", nativeQuery = true)
	Double getAverageLearningTimeCourses(int id);
	@Query(value = "select stddev(plus) from (select sum(l.estimate_learning_time) as plus from lecture l INNER JOIN course_lecture cl ON cl.lecture_id = l.id where l.lecturer_id = :id GROUP BY cl.course_id) as r", nativeQuery = true)
	Double getDesviationLearningTimeCourses(int id);
	@Query(value = "select max(plus) from (select sum(l.estimate_learning_time) as plus from lecture l INNER JOIN course_lecture cl ON cl.lecture_id = l.id where l.lecturer_id = :id GROUP BY cl.course_id) as r", nativeQuery = true)
	Double getMaxLearningTimeCourses(int id);
	@Query(value = "select min(plus) from (select sum(l.estimate_learning_time) as plus from lecture l INNER JOIN course_lecture cl ON cl.lecture_id = l.id where l.lecturer_id = :id GROUP BY cl.course_id) as r", nativeQuery = true)
	Double getMinLearningTimeCourses(int id);

}
