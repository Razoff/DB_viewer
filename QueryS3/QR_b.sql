SELECT title, nb
FROM(
  SELECT ts.S_TITLE AS title, count(aw.AW_ID) AS nb
  FROM TITLE_SERIES ts, TITLE tt, AWARDS aw, TITLE_AWARD taw
  where aw.AW_ID = taw.AWARD_ID and tt.T_ID = taw.TITLE_ID and tt.SERIES_ID = ts.S_ID
  group by ts.S_TITLE
  order by -nb
)
where ROWNUM < 11
