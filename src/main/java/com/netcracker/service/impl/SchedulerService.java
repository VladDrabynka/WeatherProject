package com.netcracker.service.impl;

import com.netcracker.configuration.SchedulerConfiguration;
import com.netcracker.model.WeatherInfo;
import com.netcracker.model.entites.GeneralInfo;
import com.netcracker.model.repository.GeneralRepository;
import com.netcracker.model.repository.TemperatureRepository;
import com.netcracker.model.repository.TimeRepository;
import com.netcracker.service.WeatherService;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

@Service
@PropertySource("classpath:/scheduler.properties")
public class SchedulerService {

    private final static Logger logger = Logger.getLogger(SchedulerService.class);
    private String SCHEDULE_CONF_FILE_NAME = "scheduler.properties";

    @Autowired
    WeatherService weatherService;
    @Autowired
    ConvertationService convertationService;

    @Autowired
    GeneralRepository generalRepository;
    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    TimeRepository timeRepository;


    public SchedulerService() {
        this(20000); //10 seconds
    }

    public SchedulerService(long refreshTime) {
        setFixedRate(String.valueOf(refreshTime));
    }

    @Scheduled(fixedRate = 20000)
    public void refreshDatabase() {
        WeatherInfo info = weatherService.currentWeatherAtCity(698740);
        GeneralInfo generalInfo = convertationService.convertToGeneral(info);
        generalRepository.save(generalInfo);

        logger.info("Current weather was saved!");
    }

    public String setFixedRate(String fixedRate){
        String finalProperty = "";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(SCHEDULE_CONF_FILE_NAME).getFile());
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
            PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
            layout.load(new InputStreamReader(new FileInputStream(file)));

            config.setProperty("fixed_delay", fixedRate);
            layout.save(new FileWriter(file, false));

            //configuration.configureTasks(new ScheduledTaskRegistrar());

            finalProperty = config.getProperty("fixed_delay").toString();
        } catch (IOException | ConfigurationException | NullPointerException ex){
            logger.error("Exception in ScheduleService.setFixedRate(): ", ex);
        }
        return finalProperty;
    }
}
