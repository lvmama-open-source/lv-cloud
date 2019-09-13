

package jedis;

import com.lv.cloud.jedis.annotation.EnableJedisCluster;
import com.lv.cloud.jedis.JedisClusterAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(locations={"classpath:jedis.properties"})
@EnableJedisCluster
public class JedisClusterPoolTest {
	
	@Autowired
	private JedisClusterAdapter jedisClusterAdapter;
	
	@Test
	public void testAdd(){
		jedisClusterAdapter.set("testJedisPool1", 60, "testJedisPool1");
		
		jedisClusterAdapter.setnx("testJedisPool12", "testJedisPool2");
	}
	
	/** 
     * 新增 
     * <br>------------------------------<br> 
     */  
    @Test
    public void testAddUser() {  
        User user = new User();  
        user.setId("user18");
        user.setName("java2000_w8");  
        User.UserOtherInfo userOtherInfo = new User.UserOtherInfo();
        userOtherInfo.setAge(25);
        User.StudyInfo studyInfo = new User.StudyInfo();
        studyInfo.setLevel("18");
        studyInfo.setSchoolName("上海交大");
        userOtherInfo.setStudyInfo(studyInfo);
        user.getUserOtherInfoMap().put("userOtherInfo18", userOtherInfo);
        //jedisClusterAdapter.setnx(user.getId(), user);
        jedisClusterAdapter.set(user.getId(), 60, user);
    }
	
//	@Test
//	public void testAddList(){
//		List<User> userList = new ArrayList<User>();
//		User user = new User();  
//        user.setId("user18");  
//        user.setName("java2000_w8");  
//        User.UserOtherInfo userOtherInfo = new User.UserOtherInfo();
//        userOtherInfo.setAge(25);
//        User.StudyInfo studyInfo = new User.StudyInfo();
//        studyInfo.setLevel("18");
//        studyInfo.setSchoolName("上海交大");
//        userOtherInfo.setStudyInfo(studyInfo);
//        user.getUserOtherInfoMap().put("userOtherInfo18", userOtherInfo);
//        userList.add(user);
//        User user2 = new User();  
//        user2.setId("user18");  
//        user2.setName("java2000_w8");  
//        User.UserOtherInfo userOtherInfo2 = new User.UserOtherInfo();
//        userOtherInfo.setAge(25);
//        User.StudyInfo studyInfo2 = new User.StudyInfo();
//        studyInfo2.setLevel("18");
//        studyInfo2.setSchoolName("上海交大");
//        userOtherInfo2.setStudyInfo(studyInfo2);
//        user2.getUserOtherInfoMap().put("userOtherInfo18", userOtherInfo2);
//		 List<User.UserOtherInfo> userInfoList = new ArrayList<JedisClusterPoolTest.User.UserOtherInfo>();
//		 userInfoList.add(userOtherInfo);
//		 user2.setUserInfoList(userInfoList);
//        userList.add(user2);
//        jedisClusterAdapter.set("userList189", 6000, userList);
//	}
	
	  @Test
	  public void testGetUser() {
	      String id = "user18";
		  User user = jedisClusterAdapter.get(id,User.class);
	      System.out.println(user);
	  }
//	
//	  @Test
//	  public void testHset(){
//		 boolean isOk = jedisClusterAdapter.hset("beanName.testMethod.arg1_arg2", String.valueOf(1), "dwdasdasd");
//		 System.out.println(isOk);
//	  }
//	  
//	  @Test
//	  public void testHget(){
//		  Object o = jedisClusterAdapter.hget("beanName.testMethod.arg1_arg2",String.valueOf(1));
//		  System.out.println(o);
//	  }
//	  
//	  public static void main(String[] args) {
//			List<User> userList = new ArrayList<User>();
//			User user = new User();  
//	        user.setId("user18");  
//	        user.setName("java2000_w8");  
//	        User.UserOtherInfo userOtherInfo = new User.UserOtherInfo();
//	        userOtherInfo.setAge(25);
//	        User.StudyInfo studyInfo = new User.StudyInfo();
//	        studyInfo.setLevel("18");
//	        studyInfo.setSchoolName("上海交大");
//	        userOtherInfo.setStudyInfo(studyInfo);
//	        user.getUserOtherInfoMap().put("userOtherInfo18", userOtherInfo);
//	        userList.add(user);
//	        User user2 = new User();  
//	        user2.setId("user18");  
//	        user2.setName("java2000_w8");  
//	        User.UserOtherInfo userOtherInfo2 = new User.UserOtherInfo();
//	        userOtherInfo.setAge(25);
//	        User.StudyInfo studyInfo2 = new User.StudyInfo();
//	        studyInfo2.setLevel("18");
//	        studyInfo2.setSchoolName("上海交大");
//	        userOtherInfo2.setStudyInfo(studyInfo2);
//	        user2.getUserOtherInfoMap().put("userOtherInfo18", userOtherInfo2);
//	        List<User.UserOtherInfo> userInfoList = new ArrayList<JedisClusterPoolTest.User.UserOtherInfo>();
//	        userInfoList.add(userOtherInfo);
//	        user2.setUserInfoList(userInfoList);
//	        userList.add(user2);
//	        
//	        byte[] bytes = SerializeUtil.serialize(userList);
//	        JedisBytes jedisBytes = new JedisBytes(bytes);
//	        String str = JSON.toJSONString(jedisBytes);
//	        System.out.println(str);
//	        List<User> userList2 =  (List<User>) SerializeUtil.unserialize(JSON.parseObject(str, JedisBytes.class).getBytes()); 
//	        System.out.println(userList2);
//	}
	  
	  public static class JedisBytes implements Serializable{
		  /**
		 * 
		 */
		private static final long serialVersionUID = -1080030949794412519L;
		
		private byte[] bytes;
		
		public JedisBytes(){}
		
		public JedisBytes(byte[] bytes){
			this.bytes = bytes;
		}
		
		public byte[] getBytes() {
			return bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
	  }
    
    /** 
     * 批量删除 
     * <br>------------------------------<br> 
     */  
//    @Test  
//    public void testDeletes() {  
////        for (int i = 0; i < 15000; i++) {  
////            jedisClusterAdapter.del("user" + i);
////        } 
////    	jedisClusterAdapter.del("userList129");
//    	jedisClusterAdapter.getSet("hhh5", "hhhh");
//    	jedisClusterAdapter.getSet("hhh5",  "hhhh1");
//    	System.out.println(jedisClusterAdapter.get("hhh5"));
////    	jedisClusterAdapter.ttl("userList189");
////    	System.out.println(jedisClusterAdapter.keyExists("hhh"));
////    	System.out.println(jedisClusterAdapter.keyExists("userList189"));
//    	
//    }  
      
    /** 
     * 获取 
     * <br>------------------------------<br> 
     */  
//    @Test  
//    public void testGetUser() {  
//        String id = "user18";  
////        User user = jedisClusterAdapter.get(id, User.class);  
//        User user = (User) jedisClusterAdapter.get(id);  
//        System.out.println(user);
//    }  
    
    public static class User implements Serializable{
    	

    	/**
    	 * 
    	 */
    	private static final long serialVersionUID = -5001309970539840943L;

    	private String id;  
          
        private String name;  
          
        private String password;  
        
        private List<UserOtherInfo> userInfoList;
        
        private Map<String, UserOtherInfo> userOtherInfoMap = new HashMap<String, UserOtherInfo>();
        
        public static class UserOtherInfo implements Serializable{
        	/**
    		 * 
    		 */
    		private static final long serialVersionUID = -5051337371530316603L;
    		private Integer age;
        	private StudyInfo studyInfo;
    		public Integer getAge() {
    			return age;
    		}
    		public void setAge(Integer age) {
    			this.age = age;
    		}
    		public StudyInfo getStudyInfo() {
    			return studyInfo;
    		}
    		public void setStudyInfo(StudyInfo studyInfo) {
    			this.studyInfo = studyInfo;
    		}
        }
        
        public static class StudyInfo implements Serializable{
        	/**
    		 * 
    		 */
    		private static final long serialVersionUID = -2555994765593989022L;
    		private String schoolName;
        	private String level;
    		public String getSchoolName() {
    			return schoolName;
    		}
    		public void setSchoolName(String schoolName) {
    			this.schoolName = schoolName;
    		}
    		public String getLevel() {
    			return level;
    		}
    		public void setLevel(String level) {
    			this.level = level;
    		}
        }
      
        /** 
         * <br>------------------------------<br> 
         */  
        public User() {  
              
        }  
          
        /** 
         * <br>------------------------------<br> 
         */  
        public User(String id, String name, String password) {  
            super();  
            this.id = id;  
            this.name = name;  
            this.password = password;  
        }  
      
        /** 
         * ���id 
         * @return the id 
         */  
        public String getId() {  
            return id;  
        }  
      
        /** 
         * ����id 
         * @param id the id to set 
         */  
        public void setId(String id) {  
            this.id = id;  
        }  
      
        /** 
         * ���name 
         * @return the name 
         */  
        public String getName() {  
            return name;  
        }  
      
        /** 
         * ����name 
         * @param name the name to set 
         */  
        public void setName(String name) {  
            this.name = name;  
        }  
      
        /** 
         * ���password 
         * @return the password 
         */  
        public String getPassword() {  
            return password;  
        }  
      
        /** 
         * ����password 
         * @param password the password to set 
         */  
        public void setPassword(String password) {  
            this.password = password;  
        }

    	public Map<String, UserOtherInfo> getUserOtherInfoMap() {
    		return userOtherInfoMap;
    	}

    	public void setUserOtherInfoMap(Map<String, UserOtherInfo> userOtherInfoMap) {
    		this.userOtherInfoMap = userOtherInfoMap;
    	}

		public List<UserOtherInfo> getUserInfoList() {
			return userInfoList;
		}

		public void setUserInfoList(List<UserOtherInfo> userInfoList) {
			this.userInfoList = userInfoList;
		}

    }
}

