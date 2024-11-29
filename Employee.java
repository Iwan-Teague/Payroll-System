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

    //method promotion(employee)  method made by SaifKh
    //takes employee  takes his current role and changes it to role above him
    //example: professorFormerlyAssociateProfessor become fullProffessor
    //change scale point to 1  change salary to new role
    //exception  if job role is at maxinimum return error message
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


    // Enum to store JobCategory, that stores a unique enum JobType per JobCategory enum
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

        //use abstract to ensure every JobCategory impliments getJobTypes()
        public abstract JobType[] getJobTypes();
    }

    // Enum for all JobTypes
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

        // Methods to link correct jobRoles to correct jobCatagories
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

    // Constructor
    //pull data from excel sheet and pass name, jobCategory and jobRole. jobCategory and jobRole are enums.
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

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for PPSno
    public String getPPSno() {
        return PPSno;
    }

    // Getter for jobCategory
    public JobCategory getJobCategory() {
        return jobCategory;
    }

    // Getter for jobRole
    public JobType getJobRole() {
        return jobRole;
    }

    // Getter for payScale
    public int getPayScale() {
        return payScale;
    }

    public String getSalary() {
        return salary;
    }

    public String getUSC() {
        return usc;
    }

    public String getPRSI() {
        return prsi;
    }

    public String getPAYE() {
        return paye;
    }

    public int getAfterTaxSalary() {
        return afterTaxSalary;
    }

    // Setter for name
    public void setName(String name){
        this.name = name;
    }

    // Setter for PPSno
    public void setPPSno(String PPSno){
        this.PPSno = PPSno;
    }

    // Setter for jobCategory
    public void setJobCategory(JobCategory jobCategory){
        this.jobCategory = jobCategory;
    }

    // Setter for jobRole
    public void setJobRole(JobType jobRole){
        this.jobRole = jobRole;
    }
    // Setter for payScale
    public void setPayScale(int payScale){
        this.payScale = payScale;
    }

    public void setSalary(String salary){
        this.salary = salary;
    }

    public void setUSC(String USC){
        this.usc = USC;
    }

    public void setPRSI(String PRSI){
        this.prsi = PRSI;
    }

    public void setPAYE(String PAYE){
        this.paye = PAYE;
    }

    public void setAfterTaxSalary(int afterTaxSalary){
        this.afterTaxSalary = afterTaxSalary;
    }

    // Check if the jobRole is valid for the given jobCategory
    public boolean isRoleValidForCategory(JobCategory jobCategory, JobType jobRole) {
        for (JobType validJobRole : jobCategory.getJobTypes()) {
            if (validJobRole == jobRole) {
                return true;
            }
        }
        return false;
    }

    public void addEmployee(){
        CSVWriter.writeToCSV("Employees.csv", PPSno, name, jobCategory.toString(), jobRole.toString(), Integer.toString(payScale), userType);
    }

    @Override
    public String toString() {
        return "Employee{name= '" + name +",\tPPSno= " + PPSno  + ",\tjobCategory= " + jobCategory + ",\t\tjobRole= " + jobRole + '}';
    }

}
