# importing all the necessary libaries
import os
import re
import time
import re

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager


profile_names=[]
profile_headlines=[]
profile_location=[]
company_name=[]
company_worktime=[]
work_months=[]
work_months_total=[]




def driver_init():

    # defining the webdriver and config btw this code will be almost the same in all of your selenium scripts
    options = Options()

    # starting in maximized window
    options.add_argument("start-maximized")
    options.add_argument("--disable-default-apps")
    global driver
    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)


def login():

    # go to login page
    driver.get("https://www.linkedin.com/login/")

    # get the username and password from the environment variables
    username = os.environ.get("ln_em")
    password = os.environ.get("ln_ps")


    # typing the value in the website
    username_field = driver.find_element(By.ID,"username")
    username_field.send_keys(username)

    password_field = driver.find_element(By.ID,"password")
    password_field.send_keys(password)

    # logining in
    password_field.submit()


def scrap(url):
    
    work_months = []
    company_worktime = 0
    work_months_total = 0
    # go to emloyee profile
    driver.get(url)

    time.sleep(3)
    # scraping the name of the layoff profile
    try:
        name = driver.find_element(By.XPATH, "/html[1]/body[1]/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[1]/div[2]/div[2]/div[1]/div[1]/h1[1]").text
    except:
        name = driver.find_element(By.XPATH, "/html[1]/body[1]/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[1]/div[2]/div[2]/div[1]/div[1]/h1[1]").text

    # scraping the headline
    try:
        headline = driver.find_element(By.CLASS_NAME, "text-body-medium").text
    except:
        headline = "none"


    # scraping the location
    try:                                                                                              
        location = driver.find_element(By.XPATH, "//span[@class='text-body-small inline t-black--light break-words']").text
    except:
        location = "none"


    # scraping the company

    try:                                        
        company = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[1]/span[2]").text
    except:
        try:
            company = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[3]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[1]/span[2]").text
        except:
            try:
                company = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[1]/span[2]").text
            except:
                company = driver.find_element(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[1]/div[2]/div[2]/ul[1]/li[1]/button[1]/span[1]/div[1]").text
                                                        
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight/.1);")
    time.sleep(3)
    # scraping the company_worktime
    try:                                              
        worktime = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text                          
    except:                                              
        try:
            worktime = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[3]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text
        except:
            try:
                worktime = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text
            except:
                try:
                    worktime = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/a[1]/div[1]/span[2]/span[1]").text
                except:
                    try:
                        worktime = driver.find_element(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul[1]/li[1]/div[1]/div[2]/div[1]/a[1]/div[1]/span[2]/span[1]").text
                    except:    
                        try:
                            worktime = driver.find_element(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text
                        except:
                            try:
                                worktime = driver.find_element(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[3]/div[3]/ul/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text
                            except:
                                worktime = driver.find_element(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul/li[1]/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]").text
    str1='y' 
    str2='m'

    worktime_nospaces=re.sub(r"\s+", "", worktime)
    print("this is worktime: ", worktime)
    print("this is worktime_nospaces: ", worktime_nospaces)
    if re.findall(r"(\d+)y", worktime_nospaces) != [] and re.findall(r"(\d+)m", worktime_nospaces) != []:
        if str1 in worktime_nospaces and isinstance(int(re.findall(r"(\d+)y", worktime_nospaces)[0]), int) and str2 in worktime_nospaces and isinstance(int(re.findall(r"(\d+)m", worktime_nospaces)[0]), int) :
            worktime_years=re.findall(r"(\d+)y", worktime_nospaces)
            worktime_monthsy=int(worktime_years[0])*12
            worktime_months=int(re.findall(r"(\d+)m", worktime_nospaces)[0])
            company_worktime=worktime_monthsy+worktime_months
    else:
        if str2 in worktime_nospaces and isinstance(int(re.findall(r"(\d+)m", worktime_nospaces)[0]), int):
            company_worktime=int(re.findall(r"(\d+)m", worktime_nospaces)[0])

        elif str1 in worktime_nospaces and isinstance(int(re.findall(r"(\d+)y", worktime_nospaces)[0]), int):
            worktime_years=re.findall(r"(\d+)y", worktime_nospaces)
            company_worktime=int(worktime_years[0])*12

    # scraping the years of experience
    try:    
        experiences = driver.find_elements(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul[1]/li/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]")
        print(experiences[0])
    except:
        try:
            experiences = driver.find_elements(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[3]/div[3]/ul[1]/li/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]")
            print(experiences[0])
        except:
            try:
                experiences = driver.find_elements(By.XPATH, "//body/div[5]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul[1]/li/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]")
                print(experiences[0])
            except:
                try:
                    experiences = driver.find_elements(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[5]/div[3]/ul[1]/li/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]")
                    print(experiences[0])      
                except:
                    experiences = driver.find_elements(By.XPATH, "//body/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/section[4]/div[3]/ul[1]/li/div[1]/div[2]/div[1]/div[1]/span[2]/span[1]")
                    print(experiences[0])      
    for experience in experiences:
        if experience.text == "":
            pass
        else:
            years=experience.text
        str1='y'
        str2='m'
        print(years)
        experience_nospaces=re.sub(r"\s+", "", years)
        if re.findall(r"(\d+)y", experience_nospaces) != [] and re.findall(r"(\d+)m", experience_nospaces) != []:
            if str1 in experience_nospaces and isinstance(int(re.findall(r"(\d+)y", experience_nospaces)[0]), int) and str2 in experience_nospaces and isinstance(int(re.findall(r"(\d+)m", experience_nospaces)[0]), int) :
                experience_years=re.findall(r"(\d+)y", experience_nospaces)
                experience_monthsy=int(experience_years[0])*12
                experience_months=int(re.findall(r"(\d+)m", experience_nospaces)[0])
                experience_months_total=experience_monthsy+experience_months
                work_months.append(experience_months_total)
        else:
            if str2 in experience_nospaces and isinstance(int(re.findall(r"(\d+)m", experience_nospaces)[0]), int):
                experience_months=int(re.findall(r"(\d+)m", experience_nospaces)[0])
                work_months.append(experience_months)
                
            elif str1 in experience_nospaces and isinstance(int(re.findall(r"(\d+)y", experience_nospaces)[0]), int):
                experience_years=re.findall(r"(\d+)y", experience_nospaces)
                experience_monthsy=int(experience_years[0])*12
                work_months.append(experience_monthsy)

    work_months_total= sum(work_months)
    
    # returning the data in a dictionary
    return ({"name":name, "headline": headline, "location":location, "company":company, "company_worktime":company_worktime, "work_months":work_months_total})


def fetch_by_url(url, initialized):
    
    if initialized == False:
        # initiliaze driver
        driver_init()

        # login to Linkedin
        login()
    

    # go to profile and scrap data
    data = scrap(url)

    # driver.close()

    return data

# fetch_by_url("https://www.linkedin.com/in/taha-elghabi/")