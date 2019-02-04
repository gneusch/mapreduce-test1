package esco

case class Self(href: String, title: String, uri: String)
case class TopConceptList(hasTopConcept: List[Self], self: Self)
case class PreferredLabel(bg: String, cs: String, da: String, de: String, el: String, en: String, es: String, et: String, fi: String, fr: String, ga: String, hr: String, hu: String, is: String, it: String, lt: String, lv: String, mt: String, nl: String, no: String, pl: String, pt: String, ro: String, sk: String, sl: String, sv: String)
case class SkillList(links: TopConceptList, classId: String, className: String, preferredLabel: PreferredLabel, title: String, uri: String)



class Skill {
  final val SKILL_LIST_EN_URL = "http://data.europa.eu/esco/concept-scheme/skills&language=en"
}
