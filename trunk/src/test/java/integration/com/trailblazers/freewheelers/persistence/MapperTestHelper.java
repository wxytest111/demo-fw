package integration.com.trailblazers.freewheelers.persistence;

import com.trailblazers.freewheelers.mappers.AccountMapper;
import com.trailblazers.freewheelers.mappers.ItemMapper;
import com.trailblazers.freewheelers.persistence.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

public abstract class MapperTestHelper {
    private SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.rollback();
        sqlSession.close();
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }

}
