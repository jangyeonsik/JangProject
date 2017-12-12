package com.sist.manager;

import java.util.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.MovieDAO;
import com.sist.dao.MovieVO;

public class MovieManager {
	
	//http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date=20171205
	public List<String> movieLinkData() {
		List<String> list = new ArrayList<String>();
		int k=1;
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(int i=1; i<=40; i++) {
				Document doc = Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date="+sdf.format(date)+"&page="+i).get();
				Elements elem = doc.select("td.title div.tit5 a");
				for(int j=0; j<elem.size(); j++) {
					Element code=elem.get(j);
					//System.out.println(k+":"+code.attr("href"));
					list.add("http://movie.naver.com"+code.attr("href"));
					k++;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<MovieVO> movieDetailData(){
		List<MovieVO> list = new ArrayList<MovieVO>();
		MovieDAO dao = new MovieDAO();
		
		try {
			List<String> linkList = movieLinkData();
			
			for(int i=0; i<linkList.size(); i++) {
				
				try {
				String link=linkList.get(i);
				Document doc= Jsoup.connect(link).get();
				
				Element title = doc.select("div.mv_info h3.h_movie a").first();
				Element director = doc.select("div.info_spec2 dl.step1 dd a").first();
				Element actor = doc.select("div.info_spec2 dl.step2 dd a").first();
				
				Element poster = doc.selectFirst("div.poster a img");
				Elements temp = doc.select("p.info_spec span");
				
				
				Element genre = temp.get(0);
				Element time = temp.get(2);
				Element regdate = temp.get(3);
				Element grade = temp.get(4);
				//Element soso = doc.selectFirst("");
				Element story = doc.selectFirst("div.story_area p.con_tx");
//				System.out.println((i+1)+":"+title.text()+"=="+director.text()+"=="+actor.text()+"=="+genre.text()+"=="+time.text()
//				+"=="+regdate.text()+"=="+grade.text());
				//System.out.println((i+1)+":"+poster.attr("src"));
				System.out.println((i+1)+":"+title.text());
				
				MovieVO vo = new MovieVO();
				vo.setMno(i+1);
				vo.setTitle(title.text());
				vo.setDirector(director.text());
				vo.setActor(actor.text());
				vo.setPoster(poster.attr("src"));
				vo.setGenre(genre.text());
				vo.setGrade(grade.text());
				vo.setTime(time.text());
				vo.setRegdate(regdate.text());
				String s = story.text();
				s = s.replace("'", " ");
				s = s.replace("\"", "");
				vo.setStory(s);
				dao.movieInsert(vo);
				list.add(vo);
				
				
				}catch(Exception ex) {}
						
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		MovieManager mv=new MovieManager();
		mv.movieDetailData();
		System.out.println("저장완료");
	}
}
