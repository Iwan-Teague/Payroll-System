# Payroll System

## Overview
The **Payroll System** has been designed categorize and manage employees at the University of Limerick. By reading data from various csv files, 
it is able to calulate salaries, taxes and generates payslips. This information can be accessed using the command line interface (CLI) where employees
log in using their credentials. Certain employees have exclusive functionality like HR who can promote other employees and Admins who can add new 
employees to the system

### Key Functionalities:
- **Employee Data Management**: Reads from an `employees.csv` file containing employee details such as name, PPS number, department, occupation, and pay scale.
- **Salary and Tax Calculations**: Automatically computes salaries, PRSI, PAYE, USC, and after-tax income.
- **Payslip Storage and Access**: Stores all data in `Payslips.csv`, which employees can access through a command-line interface (CLI).
- **Role-Based Access**: 
  - Employees can log in to view payslips and manage promotions.
  - Admins can add users to the system.
  - Human Resource (HR) users can promote employees.
- **Promotion Handling**: Promoted employees can accept or decline promotions when they log in.

### Target Audience:
The system has been tailor made for the employees of the University of Limerick, specifically it's staff.

---

## Features
- Login system for employees, admins, and HR users.
- Salary and tax information calculated using employee pay scales.
- Payslip storage and viewability.
- Administrative tools for adding users.
- HR tools for promoting employees and managing career progression.
- Promotion acceptance or declination for employees.

---

## Prerequisites

The Payroll System uses exclusivly **Java**. The following libraries were used:

- `java.io.BufferedReader`
- `java.io.FileReader`
- `java.io.FileWriter`
- `java.io.IOException`
- `java.time.LocalDate`
- `java.time.format.DateTimeFormatter`
- `java.util.Scanner`
- `java.util.*`

Github repository:
   
https://github.com/Iwan-Teague/Payroll-System.git
