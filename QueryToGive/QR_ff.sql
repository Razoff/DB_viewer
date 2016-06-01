SELECT *
FROM
(SELECT q2.lgd as lgd, ti.T_TITLE as titl,count(ti.T_TITLE) as nb
FROM
(SELECT q1.lgd as lgd, ti.PARENT as dady
FROM
(SELECT DISTINCT lg.LG_ID as lgd
FROM LANGUAGES lg) q1, TITLE ti
where ti.PARENT != 0) q2, TITLE ti
where q2.dady = ti.T_ID and ti.LANGUAGE_ID = q2.lgd
group BY q2.lgd, ti.T_TITLE)q3
