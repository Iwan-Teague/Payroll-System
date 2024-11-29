/**
 * @author Iwan Teague
 * The Employee class represents an employee with various attributes like name, job category,
 * job role, pay scale, and salary details. It also provides functionality for promoting an employee
 * to a higher job role within their category.
 */
public class Employee {

    private String PPSno;
    private String name;
    private JobCategory jobCategory;
    private JobType jobRole;
    private int payScale;
    private String userType;
    private String salary;
    private String usc;
    private String prsi;
    private String paye;
    private int afterTaxSalary;

    /**
     *
     * Promotes the given employee to a higher job role within their category.
     * If the current job role is the highest in the category, an exception is thrown.
     *
     * @param employee The employee to be promoted.
     * @throws IllegalArgumentException if the job role is already at the highest in the category.
     */
    public void promotion(Employee employee) {
        int currentOrdinal = employee.getJobRole().ordinal();
        //highest job positions ordinal values  in each category are : 0 2 8 15 19 25 34 39 49 52 55 61
        //check if job role is the highest in the job category
        if(currentOrdinal == 0 || currentOrdinal == 2 ||currentOrdinal == 8 ||currentOrdinal == 15 ||currentOrdinal == 19 ||currentOrdinal == 25 ||currentOrdinal == 34 ||currentOrdinal == 49 ||currentOrdinal == 52 ||currentOrdinal == 55 ||currentOrdinal == 61){
            throw new IllegalArgumentException("current Job Role is the highest in the category ");
        }else {
            // well be changing job role Via changing ordinal value  in enum every constant has enum value in our case the more senior role has a lower ordinal value
            int newOrdinal = currentOrdinal - 1;
            JobType newRole = JobType.values()[newOrdinal];
            employee.setJobRole(newRole);
            System.out.println("New Job Role: " + newRole);
            employee.setPayScale(1);
        }
    }


    /**
     * @author Iwan Teague
     * Enum to store job categories and their associated job types.
     * Each category is linked to a set of job types specific to that category.
     */
    public enum JobCategory {
        presidential {
            public JobType[] getJobTypes() {
                return JobType.valuesPresidential();
            }
        },
        academic {
            public JobType[] getJobTypes() {
                return JobType.valuesAcademic();
            }
        },
        administrative {
            public JobType[] getJobTypes() {
                return JobType.valuesAdministrative();
            }
        },
        education {
            public JobType[] getJobTypes() {
                return JobType.valuesEducation();
            }
        },
        library {
            public JobType[] getJobTypes() {
                return JobType.valuesLibrary();
            }
        },
        it {
            public JobType[] getJobTypes() {
                return JobType.valuesIT();
            }
        },
        technical {
            public JobType[] getJobTypes() {
                return JobType.valuesTechnical();
            }
        },
        serviceStaff {
            public JobType[] getJobTypes() {
                return JobType.valuesServiceStaff();
            }
        },
        teachers {
            public JobType[] getJobTypes() {
                return JobType.valuesTeachers();
            }
        },
        clinical {
            public JobType[] getJobTypes() {
                return JobType.valuesClinical();
            }
        },
        ulac {
            public JobType[] getJobTypes() {
                return JobType.valuesULAC();
            }
        },
        research {
            public JobType[] getJobTypes() {
                return JobType.valuesResearch();
            }
        };

        /**
         * @author Iwan Teague
         * Abstract method that ensures each JobCategory implements a method to get job types.
         *
         * @return An array of JobType values for the specific category.
         */
        public abstract JobType[] getJobTypes();
    }

    /**
     * @author Iwan Teague
     * Enum representing all job roles (JobTypes) across different categories.
     */
    public enum JobType {
        // Presidential category
        president,
        vicePresident,

        // Academic category
        fullProfessor,
        professorFormerlyAssociateProfessor,
        associateProfessorAFormerlySeniorLecturer,
        associateProfessorBFormerlyLecturer,
        assistantProfessorFormerlyLecturerBelowTheBar,
        teachingAssistant,

        // Administrative category
        seniorAdministrativeOfficer3,
        seniorAdministrativeOfficer2,
        seniorAdministrativeOfficer1,
        seniorAdministrativeAdministrator,
        executiveAdministrator,
        seniorAdministrator,
        administrator,

        // Education category
        epsPortfolioManager,
        epsCategoryManager,
        epsCategorySpecialistHigher,
        epsCategorySpecialist,

        // Library category
        subLibrarian,
        assistantLibrarian2,
        assistantLibrarian1,
        seniorLibraryAssistant,
        libraryAssistant,
        libraryAttendant,

        // IT category
        analystProgrammer3,
        analystProgrammer2,
        analystProgrammer1,
        seniorComputerOperator,
        computerOperator,
        printOperator2,
        printOperator1,
        computerLabAttendant,
        temporaryComputerAssistant,

        // Technical category
        chiefTechnicalOfficer,
        technicalOfficer,
        seniorTechnicalOfficer,
        seniorLabAttendant,
        laboratoryAttendant,

        // Service Staff category
        senPorterAttendant,
        porterAttendant,
        groundsSupervisor,
        groundsWorkPerson,
        seniorAide,
        groundsWorkPersonMachineAttendant,
        serviceStaff,
        serviceStaffShift,
        plantMaintenanceAide,
        groundsForePerson,

        // Teachers category
        teachingFellow,
        universityTeacher,
        associateTeacher,

        // Clinical category
        therapiesRegionalSupervisorsRegionalPlacementFacilitator,
        clinicalTutor,
        clinicalFellow,

        // ULAC category
        assistantSeniorInstructor,
        leadInstructor,
        multiActivityInstructorGrade1,
        multiActivityInstructorGrade2,
        assistantInstructor,
        coopStudents,

        // Research category
        researchAssistant,
        postDoctoralResearcher,
        researchFellow,
        seniorResearchFellow;

        // methods to link correct jobRoles to correct jobCatagories
        public static JobType[] valuesPresidential() {
            return new JobType[]{ president, vicePresident };
        }

        public static JobType[] valuesAcademic() {
            return new JobType[]{
                    fullProfessor,
                    professorFormerlyAssociateProfessor,
                    associateProfessorAFormerlySeniorLecturer,
                    associateProfessorBFormerlyLecturer,
                    assistantProfessorFormerlyLecturerBelowTheBar,
                    teachingAssistant
            };
        }

        public static JobType[] valuesAdministrative() {
            return new JobType[]{
                    seniorAdministrativeOfficer3,
                    seniorAdministrativeOfficer2,
                    seniorAdministrativeOfficer1,
                    seniorAdministrativeAdministrator,
                    executiveAdministrator,
                    seniorAdministrator,
                    administrator
            };
        }

        public static JobType[] valuesEducation() {
            return new JobType[]{
                    epsPortfolioManager,
                    epsCategoryManager,
                    epsCategorySpecialistHigher,
                    epsCategorySpecialist
            };
        }

        public static JobType[] valuesLibrary() {
            return new JobType[]{
                    subLibrarian,
                    assistantLibrarian2,
                    assistantLibrarian1,
                    seniorLibraryAssistant,
                    libraryAssistant,
                    libraryAttendant
            };
        }

        public static JobType[] valuesIT() {
            return new JobType[]{
                    analystProgrammer3,
                    analystProgrammer2,
                    analystProgrammer1,
                    seniorComputerOperator,
                    computerOperator,
                    printOperator2,
                    printOperator1,
                    computerLabAttendant,
                    temporaryComputerAssistant
            };
        }

        public static JobType[] valuesTechnical() {
            return new JobType[]{
                    chiefTechnicalOfficer,
                    technicalOfficer,
                    seniorTechnicalOfficer,
                    seniorLabAttendant,
                    laboratoryAttendant
            };
        }

        public static JobType[] valuesServiceStaff() {
            return new JobType[]{
                    senPorterAttendant,
                    porterAttendant,
                    groundsSupervisor,
                    groundsWorkPerson,
                    seniorAide,
                    groundsWorkPersonMachineAttendant,
                    serviceStaff,
                    serviceStaffShift,
                    plantMaintenanceAide,
                    groundsForePerson
            };
        }

        public static JobType[] valuesTeachers() {
            return new JobType[]{
                    teachingFellow,
                    universityTeacher,
                    associateTeacher
            };
        }

        public static JobType[] valuesClinical() {
            return new JobType[]{
                    therapiesRegionalSupervisorsRegionalPlacementFacilitator,
                    clinicalTutor,
                    clinicalFellow
            };
        }

        public static JobType[] valuesULAC() {
            return new JobType[]{
                    assistantSeniorInstructor,
                    leadInstructor,
                    multiActivityInstructorGrade1,
                    multiActivityInstructorGrade2,
                    assistantInstructor,
                    coopStudents
            };
        }

        public static JobType[] valuesResearch() {
            return new JobType[]{
                    researchAssistant,
                    postDoctoralResearcher,
                    researchFellow,
                    seniorResearchFellow
            };
        }
    }

    /**
     * @author Iwan Teague
     * Constructor for creating an Employee object.
     *
     * @param name The name of the employee.
     * @param PPSno The PPS number of the employee.
     * @param jobCategory The category of the job the employee holds.
     * @param jobRole The specific role the employee holds in the job category.
     * @param payScale The pay scale assigned to the employee.
     * @throws IllegalArgumentException if the jobRole is invalid for the given jobCategory.
     */
    public Employee( String name, String PPSno, JobCategory jobCategory, JobType jobRole, int payScale) {
        if (!isRoleValidForCategory(jobCategory, jobRole)) {
            throw new IllegalArgumentException("Invalid JobRole for the given JobCategory");
        }
        this.PPSno = PPSno;
        this.name = name;
        this.jobCategory = jobCategory;
        this.jobRole = jobRole;
        this.payScale = payScale;
    }

    /**
     * @author Iwan Teague
     * Constructor for creating an Employee object.
     *
     * @param name The name of the employee.
     * @param PPSno The PPS number of the employee.
     * @param jobCategory The category of the job the employee holds.
     * @param jobRole The specific role the employee holds in the job category.
     * @param payScale The pay scale assigned to the employee.
     * @param userType The user type of the user. Used in CLI
     * @throws IllegalArgumentException if the jobRole is invalid for the given jobCategory.
     */
    public Employee( String name, String PPSno, JobCategory jobCategory, JobType jobRole, int payScale, String userType) {
        if (!isRoleValidForCategory(jobCategory, jobRole)) {
            throw new IllegalArgumentException("Invalid JobRole for the given JobCategory");
        }
        this.PPSno = PPSno;
        this.name = name;
        this.jobCategory = jobCategory;
        this.jobRole = jobRole;
        this.payScale = payScale;
        this.userType = userType;
    }

    /**
     * @author Iwan Teague
     * Returns the name of the employee.
     *
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @author Iwan Teague
     * Returns the PPS number of the employee.
     *
     * @return The employee's PPS number.
     */
    public String getPPSno() {
        return PPSno;
    }

    /**
     * @author Iwan Teague
     * Returns the job category of the employee.
     *
     * @return The employee's job category.
     */
    public JobCategory getJobCategory() {
        return jobCategory;
    }

    /**
     * @author Iwan Teague
     * Returns the job role of the employee.
     *
     * @return The employee's job role.
     */
    public JobType getJobRole() {
        return jobRole;
    }

    /**
     * @author Iwan Teague
     * Returns the pay scale of the employee.
     *
     * @return The employee's pay scale.
     */
    public int getPayScale() {
        return payScale;
    }

    /**
     * @author Iwan Teague
     * Returns the salary of the employee.
     *
     * @return The employee's salary.
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @author Iwan Teague
     * Returns the USC of the employee.
     *
     * @return The employee's USC.
     */
    public String getUSC() {
        return usc;
    }

    /**
     * @author Iwan Teague
     * Returns the PRSI of the employee.
     *
     * @return The employee's PRSI.
     */
    public String getPRSI() {
        return prsi;
    }

    /**
     * @author Iwan Teague
     * Returns the PAYE of the employee.
     *
     * @return The employee's PAYE.
     */
    public String getPAYE() {
        return paye;
    }

    /**
     * @author Iwan Teague
     * Returns the after tax salary of the employee.
     *
     * @return The employee's after tax salary.
     */
    public int getAfterTaxSalary() {
        return afterTaxSalary;
    }

    /**
     * @author Iwan Teague
     * Sets the name of the employee.
     *
     * @param name The new name of the employee.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @author Iwan Teague
     * Sets the PPS number of the employee.
     *
     * @param PPSno The new PPS number of the employee.
     */
    public void setPPSno(String PPSno){
        this.PPSno = PPSno;
    }

    /**
     * @author Iwan Teague
     * Sets the job category of the employee.
     *
     * @param jobCategory The new job category of the employee.
     */
    public void setJobCategory(JobCategory jobCategory){
        this.jobCategory = jobCategory;
    }

    /**
     * @author Iwan Teague
     * Sets the job role of the employee.
     *
     * @param jobRole The new job role of the employee.
     */
    public void setJobRole(JobType jobRole){
        this.jobRole = jobRole;
    }

    /**
     * @author Iwan Teague
     * Sets the pay scale of the employee.
     *
     * @param payScale The new pay scale of the employee.
     */
    public void setPayScale(int payScale){
        this.payScale = payScale;
    }

    /**
     * @author Iwan Teague
     * Sets the salary of the employee.
     *
     * @param salary The new salary of the employee.
     */
    public void setSalary(String salary){
        this.salary = salary;
    }

    /**
     * @author Iwan Teague
     * Sets the USC of the employee.
     *
     * @param USC The USC of the employee based on salary.
     */
    public void setUSC(String USC){
        this.usc = USC;
    }

    /**
     * @author Iwan Teague
     * Sets the PRSI of the employee.
     *
     * @param PRSI The PRSI of the employee based on salary.
     */
    public void setPRSI(String PRSI){
        this.prsi = PRSI;
    }

    /**
     * @author Iwan Teague
     * Sets the PAYE of the employee.
     *
     * @param PAYE The PAYE of the employee based on salary.
     */
    public void setPAYE(String PAYE){
        this.paye = PAYE;
    }

    /**
     * @author Iwan Teague
     * Sets the after tax salary of the employee.
     *
     * @param afterTaxSalary The after tax salary of the employee based on salary.
     */
    public void setAfterTaxSalary(int afterTaxSalary){
        this.afterTaxSalary = afterTaxSalary;
    }

    /**
     * @author Iwan Teague
     * Checks if the job role is valid for the given job category.
     *
     * @param jobCategory The job category.
     * @param jobRole The job role.
     * @return True if the job role is valid for the category, false otherwise.
     */
    public boolean isRoleValidForCategory(JobCategory jobCategory, JobType jobRole) {
        for (JobType validJobRole : jobCategory.getJobTypes()) {
            if (validJobRole == jobRole) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author Iwan Teague
     * adds an employees PPSno, name, job category, job role, pay scale and user type to the Employees.csv
     */
    public void addEmployee(){
        CSVWriter.writeToCSV("Employees.csv", PPSno, name, jobCategory.toString(), jobRole.toString(), Integer.toString(payScale), userType);
    }

    /**
     * @author Iwan Teague
     * Returns a string representation of the Employee object.
     *
     * @return A string describing the employee's attributes.
     */
    @Override
    public String toString() {
        return "Employee{name= '" + name +",\tPPSno= " + PPSno  + ",\tjobCategory= " + jobCategory + ",\t\tjobRole= " + jobRole + '}';
    }

}
