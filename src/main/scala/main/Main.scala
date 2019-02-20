package main

import esco.{EscoQueuingSkillHttp, EscoSkill, EscoSkillHttp}
import jobposting.{JobPostingCreator, JobPostingFile}
import textmining.{HtmlUtils, TextUtils}
import utils.Languages

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Main extends App {

  def getSkillLabelList = {
    val lang = Languages.EN
    val escoSkillHttp = new EscoQueuingSkillHttp(8192)

    val escoSkillList = escoSkillHttp.getListOfSkills(lang)

    val skillTitleList = escoSkillList map {
      skillList => skillList flatMap {
        skill => {
          val preferredLabel = skill.preferredLabel.enLabel
          val alternativeLabel = skill.alternativeLabel match {
            case Some(alternateLabel) => lang.name match {
              case Languages.HU.name => alternateLabel.getLabelList(Languages.HU)
              case _ => alternateLabel.getLabelList(Languages.EN)
            }
            case None => List()
          }
          preferredLabel :: alternativeLabel
        }
      }
    }

    skillTitleList
  }

  def printLabelList(skillTitleList: Future[List[String]]) = {
    Await.result(skillTitleList, Duration.Inf) map {
      var i = 0
      title => {
        i = i + 1
        println(s"$i: $title")
      }
    }
  }

  def findBiGramsInSkillLabels = {
    import textmining.TextUtils._

    val jobPostingDir = s"/home/answeris42/Workspace/scraper/JobPostingScraper/data/"
    val extensions = List("jl")
    val jobPostingTitleDescTupleFuture = Future {
      JobPostingFile.getFromDir(jobPostingDir, extensions) flatMap {
        jobposting => jobposting.getJobPostings
      } map {
        jobposting => (
          jobposting.titlePosting,
          genBiGrams(strListToLower(jobposting.jobDescriptionToStringList)) map {
            case (str1, str2) => s"$str1 $str2"
          }
        )
      }
    }
    /*val escoLabelsFuture = getSkillLabelList

    val postingDescEscoSkillMap = jobPostingTitleDescTupleFuture flatMap {
      jPTDT => escoLabelsFuture map {
        escoLabels => jPTDT map {
          jobPosting => (jobPosting._1, jobPosting._2.filter( biGram => escoLabels.exists(_.contains(biGram))), escoLabels.filter( label => jobPosting._2.exists(label.contains(_))))
        }
      }
    }

    postingDescEscoSkillMap*/
    Await.result(jobPostingTitleDescTupleFuture, Duration.Inf) map {
      jobposting => println(jobposting)
    }

  }

  /*Await.result(findBiGramsInSkillLabels, Duration.Inf) map {
    postingTupples => println(postingTupples._1, postingTupples._2, postingTupples._3)
  }*/
  findBiGramsInSkillLabels
}
