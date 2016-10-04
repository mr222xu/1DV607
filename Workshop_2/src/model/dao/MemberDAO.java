package model.dao;

import java.util.List;

import model.Member;

public interface MemberDAO {
	public void create(Member m);
	public void delete(Member m);
	public void update(Member m);
	public List<Member> getMembers();
	public int getNextId();
}
