package gl.passhelper.controller;

import gl.passhelper.dao.passhelper.TestMapper;
import gl.passhelper.data.DO.TestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/index")
    public TestDO index() {
        return testMapper.selectById(1);
    }
}
