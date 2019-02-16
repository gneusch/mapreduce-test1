package jobposting

import java.io.File

import org.joda.time.{DateTime => JodaDateTime}
import utils.JobPostingJsonUtils
import spray.json._

import scala.io.Source
//TODO use JavaConverters
import scala.collection.JavaConversions._
import org.apache.commons.io.FileUtils
import textmining.HtmlUtils

case class JobPosting (
                        crawlingDate: JodaDateTime,
                        postingId: String,
                        titleResultPage: String,
                        titlePosting: String,
                        company: String,
                        companyRatingValue: Double,
                        companyRatingCount: Int,
                        jobDescription: List[String],
                        postingTime: String,
                        jobLocation: String
                      ){
  def jobDescriptionToStringList: List[String] = {
    HtmlUtils.removeMarkup(jobDescription.mkString)
      .replaceAll("[^\\p{L}\\p{Nd}]+", " ")
      .split("\\s+")
      .map(_.trim)
      .filter(_.nonEmpty)
      .toList
  }
}

object JobPostingCreator extends JobPostingJsonUtils {
  def fromJsonLine(jobPostingString: String): JobPosting = jobPostingString.parseJson.convertTo[JobPosting]
}

case class JobPostingFile(jobPostingLines: List[String]) {
  def getJobPostings: List[JobPosting] = jobPostingLines map {
    jobPosting => JobPostingCreator.fromJsonLine(jobPosting)
  }
}

object JobPostingFile  {
  def apply(filePath: String): JobPostingFile = {
    val bufferedSource = Source.fromFile(filePath)
    try {
      val jobPostingLines = bufferedSource.getLines()
      new JobPostingFile(jobPostingLines.toList)
    } finally{
      bufferedSource.close()
    }
  }

  def getFromDir(path: String, extensions: List[String]): List[JobPostingFile] = {
    FileUtils.listFiles(new File(path), extensions.toArray, false).map{
      f => apply(f.getAbsolutePath)
    }.toList
  }
}