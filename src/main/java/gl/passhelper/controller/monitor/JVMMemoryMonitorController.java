package gl.passhelper.controller.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/monitor/memory")
public class JVMMemoryMonitorController {

    @GetMapping("usage")
    public List<MemoryUsage> memory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        List<MemoryUsage> list = new ArrayList<>();
        list.add(heapMemoryUsage);
        list.add(nonHeapMemoryUsage);
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            list.add(usage);
        }
        return list;
    }
}
