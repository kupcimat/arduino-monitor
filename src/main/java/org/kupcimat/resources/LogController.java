package org.kupcimat.resources;

import org.kupcimat.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogDao logDao;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createLog(@RequestBody Log log) {
        logDao.saveLog(log);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Log> getAllLogs() {
        return logDao.getAllLogs();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{logType}")
    public List<Log> getFilteredLogs(@PathVariable String logType) {
        return logDao.getAllLogs(logType);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAllLogs() {
        logDao.deleteAllLogs();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
