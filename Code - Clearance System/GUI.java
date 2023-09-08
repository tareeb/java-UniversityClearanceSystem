
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;


public class GUI {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    DatabaseController database ;

    int Height , Width ; 
    
    GUI(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Width  = (int) screenSize.getWidth();
        Height = (int) screenSize.getHeight();

        frame.setTitle("Clearance System");
        frame.setSize(Width , Height);
        frame.setBackground(Color.gray);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel.setBounds(0, 0, Width , Height);
                
        panel.setLayout(null);

        mainmenu();

        frame.add(panel);
        frame.setVisible(true);
        
        try {
            database = new DatabaseController("sa", "123"); //username  / Password
        } catch (Exception e) {
            System.out.println("Error in connecting with database");
        }
       
        
    }

    public void mainmenu(){

        panel.setLayout(null);

        panel.removeAll();
		panel.updateUI();
        
        panel.setBackground(Color.gray);
        
        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(new ImageIcon("src\\background9.jpg").getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT)));
        background.setBounds(0, 0, Width, Height);
        panel.add(background);

        Boolean[] isStudent = {true}; 

        Border blackline = BorderFactory.createLineBorder(Color.black,5);
        
        JPanel action_panel = new JPanel();
        action_panel.setLayout(null);
        action_panel.setBackground(new Color(12, 73, 93));
        action_panel.setBorder(blackline);
        action_panel.setBounds(640, 250, 280, 400);
        background.add(action_panel);

        JButton student_or_faculty = new JButton("Student");        
		student_or_faculty.setFont(new Font("",Font.PLAIN,20));
        student_or_faculty.setForeground(Color.ORANGE);
		student_or_faculty.setBackground(new Color(12, 73, 93));
        student_or_faculty.setBorder(blackline);
        student_or_faculty.setBounds(50, 50, 200, 50);
        background.add(student_or_faculty);
        

        JTextField er_TextField = new JTextField(" Notifications will be displayed Here ");
		er_TextField.setForeground(Color.ORANGE);
		er_TextField.setBackground(Color.BLACK);
        er_TextField.setHorizontalAlignment(JTextField.CENTER);
		er_TextField.setFont(new Font("",Font.PLAIN,18));
		er_TextField.setBounds(1150 , 50 , 400 , 50);
        background.add(er_TextField);

        JLabel username_label = new JLabel(" Enter ID ") ;
        username_label.setForeground(Color.ORANGE); 
        username_label.setFont(new Font("",Font.PLAIN,18));
		username_label.setBounds(100 , 30 , 200 , 50);
        action_panel.add(username_label);

        JTextField username_TextField = new JTextField();
		username_TextField.setForeground(Color.ORANGE);
		username_TextField.setBackground(Color.BLACK);
        username_TextField.setHorizontalAlignment(JTextField.CENTER);
		username_TextField.setFont(new Font("",Font.PLAIN,18));
		username_TextField.setBounds(30 , 70 , 220 , 50);
        action_panel.add(username_TextField);

        JLabel Password_label = new JLabel(" Enter Password ") ;
        Password_label.setForeground(Color.ORANGE); 
        Password_label.setFont(new Font("",Font.PLAIN,18));
		Password_label.setBounds(70 , 140 , 200 , 50);
        action_panel.add(Password_label);

        JPasswordField password_TextField = new JPasswordField();
		password_TextField.setForeground(Color.ORANGE);
		password_TextField.setBackground(Color.BLACK);
        password_TextField.setHorizontalAlignment(JTextField.CENTER);
		password_TextField.setFont(new Font("",Font.PLAIN,18));
		password_TextField.setBounds(30 , 180 , 220 , 50);
        action_panel.add(password_TextField);

        JButton signin_Button = new JButton(" Sign In ");
		signin_Button.setFont(new Font("",Font.PLAIN,20));
		signin_Button.setBackground(Color.ORANGE);
        signin_Button.setBounds(30, 280, 220, 50);
        action_panel.add(signin_Button);



        //Button1
        student_or_faculty.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
                
                if(isStudent[0]){
                    student_or_faculty.setText("Faculty");
                    isStudent[0] = false ; 
                }else if(!isStudent[0]){
                    student_or_faculty.setText("Student");
                    isStudent[0] = true ; 
                }
                
            }});


        //Button 2
        signin_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {

                String userid   = username_TextField.getText().trim();
                String password = password_TextField.getText();

                if(userid.equals("")){
                    er_TextField.setText("UserId cannot Be Empty"); 
                }else if(!userid.matches("[0-9]+")){                                    // REgex // to check whether alll chaarcters are numerics
                    er_TextField.setText("User id cannot have spaces or Aplhabet");
                }else if(password.equals("")){
                    er_TextField.setText("password cannot Be Empty");
                }else if(userid.equals("0000")){
                    if(password.equals("A")){
                        adminmenu();
                    }else{
                        er_TextField.setText(" Password Incorrect ");
                    }
                }

                else {
                    

                    boolean arr[] = database.signin(isStudent[0] , userid , password  ) ; // {true,true}
                    // return boolean array // First true if username found // Second true is password match

                    if(!arr[0]){
                        er_TextField.setText(" UserId not Found "); 
                    }else if(!arr[1]){
                        er_TextField.setText(" Password Incorrect ");
                    }else {
                        er_TextField.setText(" Login Successful ");
    
                        if(userid.toLowerCase().equals("0000")){
                            if(password.equals("A")){
                                adminmenu();
                            }else{
                                er_TextField.setText(" Password Incorrect ");
                            }
                        }else if(isStudent[0]){
                            studentmenu(userid);          //student menu
                        }else{
                            facultymenu(userid);          //faculty menu
                        }
                    }
                }

            }});


    }

    public void adminmenu(){

        panel.removeAll();
		panel.updateUI();
        panel.setLayout(null);

        ImageIcon background_image = new ImageIcon("src\\background2.png");
        JLabel background = new JLabel(background_image);
        background.setBounds(0, 0, Width, Height);
        panel.add(background);

        
        JTextField er_TextField = new JTextField(" Notifications will be displayed Here ");
		er_TextField.setForeground(Color.ORANGE);
		er_TextField.setBackground(Color.BLACK);
        er_TextField.setHorizontalAlignment(JTextField.CENTER);
		er_TextField.setFont(new Font("",Font.PLAIN,18));
		er_TextField.setBounds(1230 , 30 , 340 , 50);
        background.add(er_TextField);

        JButton signout_Button = new JButton(" Sign Out ");
		signout_Button.setFont(new Font("",Font.PLAIN,20));
		signout_Button.setBackground(Color.ORANGE);
        signout_Button.setBounds(1300, 750, 200 , 50);
        background.add(signout_Button);

        signout_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
                
                panel.removeAll();
                panel.updateUI();
                mainmenu();
                
            }});

            JButton restriction_Button = new JButton(" Restrict Clearance");
            restriction_Button.setFont(new Font("",Font.PLAIN,20));
            restriction_Button.setBackground(Color.ORANGE);
            restriction_Button.setBounds(1050, 750, 200, 50);
            background.add(restriction_Button);
    
            restriction_Button.addActionListener(new ActionListener() {
                    
                public void actionPerformed(ActionEvent e) {
                    
                   int i = database.restrict_clearance();
                   if(i==0){
                       er_TextField.setText("Clearance Restricted");
                   }else if(i==1){
                    er_TextField.setText("Clearance Already Restricted");
                   }
                    
                }});

                JButton allow_Button = new JButton(" Allow Clearance");
                allow_Button.setFont(new Font("",Font.PLAIN,20));
                allow_Button.setBackground(Color.ORANGE);
                allow_Button.setBounds(800, 750, 200, 50);
                background.add(allow_Button);
        
                allow_Button.addActionListener(new ActionListener() {
                        
                    public void actionPerformed(ActionEvent e) {
                        
                        int i = database.allow_clearance() ; 
                        if(i==0){
                            er_TextField.setText("Clearance Allowed");
                        }else if(i==1){
                         er_TextField.setText("Clearance Already Allowed");
                        }
                        
                    }});

        

        ///////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////

        Border blackline = BorderFactory.createLineBorder(Color.black,5);
    
        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(1230, 100, 340, 500);
        actionPanel.setBackground(new Color(12, 89, 130));
        actionPanel.setLayout(null);
        actionPanel.setBorder(blackline);
        actionPanel.setVisible(true);
        background.add(actionPanel);
        
        TitledBorder emp_id_ComboBox_Title = BorderFactory.createTitledBorder ("Enter Employee ID");
        emp_id_ComboBox_Title.setTitleColor(new Color(251, 120, 8 ));
        emp_id_ComboBox_Title.setBorder(null);
        emp_id_ComboBox_Title.setTitleJustification(TitledBorder.CENTER);
        emp_id_ComboBox_Title.setTitlePosition(TitledBorder.TOP);
    

        JTextField emp_id_TextField  = new JTextField();
		emp_id_TextField.setForeground(Color.ORANGE);
		emp_id_TextField.setBackground(new Color(1, 1, 31 ));
        emp_id_TextField.setBorder(emp_id_ComboBox_Title);
        emp_id_TextField.setHorizontalAlignment(JTextField.CENTER);
		emp_id_TextField.setFont(new Font("",Font.PLAIN,18));
		emp_id_TextField.setBounds(30 , 50 , 280 , 60);
        actionPanel.add(emp_id_TextField);

        TitledBorder emp_dept_ComboBox_Title = BorderFactory.createTitledBorder ("Select Department");
        emp_dept_ComboBox_Title.setTitleColor(new Color(251, 120, 8 ));
        emp_dept_ComboBox_Title.setBorder(null);
        emp_dept_ComboBox_Title.setTitleJustification(TitledBorder.CENTER);
        emp_dept_ComboBox_Title.setTitlePosition(TitledBorder.BELOW_TOP);

        String s1[] = {"Main","CS","IS","EE"};
        JComboBox emp_dept_ComboBox = new JComboBox(s1);
        emp_dept_ComboBox.setForeground(Color.ORANGE);
		emp_dept_ComboBox.setBackground(Color.BLACK);
        emp_dept_ComboBox.setBorder(emp_dept_ComboBox_Title);
		emp_dept_ComboBox.setFont(new Font("",Font.PLAIN,18));
		emp_dept_ComboBox.setBounds(30 , 160 , 280 , 60);
        emp_dept_ComboBox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        actionPanel.add(emp_dept_ComboBox);

        TitledBorder priority_ComboBox_Title = BorderFactory.createTitledBorder ("Select Priority");
        priority_ComboBox_Title.setTitleColor(new Color(251, 120, 8 ));
        priority_ComboBox_Title.setBorder(null);
        priority_ComboBox_Title.setTitleJustification(TitledBorder.CENTER);
        priority_ComboBox_Title.setTitlePosition(TitledBorder.BELOW_TOP);

        String s2[] = {"1","2","3"};
        JComboBox priority_ComboBox = new JComboBox(s2);
        priority_ComboBox.setForeground(Color.ORANGE);
	    priority_ComboBox.setBackground(Color.BLACK);
        priority_ComboBox.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        priority_ComboBox.setBorder(priority_ComboBox_Title);
		priority_ComboBox.setFont(new Font("",Font.PLAIN,18));
        priority_ComboBox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		priority_ComboBox.setBounds(30 , 270 , 280 , 60);
        actionPanel.add(priority_ComboBox);

        JButton set_clearifierButton = new JButton(" Set ");
		set_clearifierButton .setFont(new Font("",Font.PLAIN,20));
		set_clearifierButton .setBackground(Color.ORANGE);
        set_clearifierButton .setBounds(30, 380, 280, 60);
        actionPanel.add(set_clearifierButton );

        set_clearifierButton.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
                
                if(emp_id_TextField.getText().equals("")){
                    er_TextField.setText("User id cannot be Empty");
                }else if(!emp_id_TextField.getText().matches("[0-9]+")){
                    er_TextField.setText("User id cannot have spaces or Aplhabet");
                }
                else{
                   
                    int emp_id   = Integer.valueOf( emp_id_TextField.getText() ) ;
                    int dept_id  = emp_dept_ComboBox.getSelectedIndex()          ;
                    int priority = priority_ComboBox.getSelectedIndex() + 1      ;

                    boolean[] arr = database.Set_Clearifiers(emp_id, dept_id, priority);

                    if(!arr[2]){
                        er_TextField.setText("Cllerarifiers cannot be > then 5");
                    }
                    else if(!arr[0]){
                        er_TextField.setText("Employee ID Not Found");
                    }else if (!arr[1]){
                        er_TextField.setText("Clearifier Already Present");
                    }else{
                        er_TextField.setText("Successfully Added");
                    }
                    
                }

                
                
                
            }});


        





        // Employee data
        ///////////////////////////////////////////////////////////////////////////////

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(new Color(12, 39, 82  ));
        dataPanel.setVisible(true);

        JScrollPane scrolldataPanel = new JScrollPane(dataPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        scrolldataPanel.setBorder(blackline);
        scrolldataPanel.setBounds(50, 100, 1150, 500);  
        scrolldataPanel.setVisible(true);
        background.add(scrolldataPanel);

        JPanel search_panel = new JPanel();  
        search_panel.setBorder(blackline);
        search_panel.setBackground(Color.BLACK);
        search_panel.setBounds(50, 30, 1150, 50); 
        search_panel.setLayout(new GridLayout( 1 , 6 , 5 , 5 ));
        search_panel.setVisible(true);
        background.add(search_panel);

        JButton    search        = new JButton(" Get Data ");
        JTextField searcheid = new JTextField();
        JTextField searchfirstname = new JTextField();
        JTextField searchlastname = new JTextField();
        JTextField searchposition = new JTextField();
        JTextField searchdname = new JTextField() ;

        search           .setFont(new Font("",Font.BOLD,20));
        searcheid	     .setFont(new Font("",Font.BOLD,20));
        searchfirstname	 .setFont(new Font("",Font.BOLD,20));
        searchlastname	 .setFont(new Font("",Font.BOLD,20));
        searchposition   .setFont(new Font("",Font.BOLD,20));
        searchdname      .setFont(new Font("",Font.BOLD,20));
        
        Color headerForegroundcolor1 = new Color(8, 22, 31 ) ;

        search      .setForeground( headerForegroundcolor1 );
        searcheid     .setForeground( headerForegroundcolor1 );
        searchfirstname	 .setForeground( headerForegroundcolor1 );
        searchlastname	 .setForeground( headerForegroundcolor1 );
        searchposition    .setForeground( headerForegroundcolor1 );
        searchdname       .setForeground( headerForegroundcolor1);

        Color headerBackgroundcolor1 = new Color(18, 61, 111) ;

        search       .setBackground( new Color(203, 67, 30) );
        searcheid	     .setBackground( headerBackgroundcolor1 );
        searchfirstname	 .setBackground( headerBackgroundcolor1 );
        searchlastname	 .setBackground( headerBackgroundcolor1 );
        searchposition     .setBackground( headerBackgroundcolor1 );
        searchdname       .setBackground( headerBackgroundcolor1);

        search_panel.add(search);
        search_panel.add(searcheid);
        search_panel.add(searchfirstname);
        search_panel.add(searchlastname);
        search_panel.add(searchposition);
        search_panel.add(searchdname);

        search.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {

                dataPanel.removeAll();
                dataPanel.updateUI();
                
                ArrayList<employee> employeeData = database.getemployeedata(searcheid.getText() , searchfirstname.getText() 
                , searchlastname.getText() , searchposition.getText() , searchdname.getText());
        

        Iterator<employee> itr = employeeData.iterator();               

        int rows =  employeeData.size(); 
        dataPanel.setLayout(new GridLayout( rows+1 , 6 , 5 , 5 ));

        

        JTextField headerindexLabel = new JTextField(" Index");
        JTextField headereidLabel = new JTextField("ID ");
        JTextField headerfirstnameLabel = new JTextField("First Name");
        JTextField headerlastnameLabel = new JTextField("Second Name");
        JTextField headerpositionLabel = new JTextField("Position");
        JTextField headerdnameLabel = new JTextField("Department") ;

        headerindexLabel.setEditable(false);
        headereidLabel.setEditable(false);
        headerfirstnameLabel.setEditable(false);
        headerlastnameLabel.setEditable(false);
        headerpositionLabel.setEditable(false);
        headerdnameLabel.setEditable(false);

        headerindexLabel       .setFont(new Font("",Font.BOLD,20));
        headereidLabel	     .setFont(new Font("",Font.BOLD,20));
        headerfirstnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerlastnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerpositionLabel    .setFont(new Font("",Font.BOLD,20));
        headerdnameLabel       .setFont(new Font("",Font.BOLD,20));
        
        Color headerForegroundcolor1 = new Color(8, 22, 31 ) ;

        headerindexLabel       .setForeground( headerForegroundcolor1 );
        headereidLabel	     .setForeground( headerForegroundcolor1 );
        headerfirstnameLabel	 .setForeground( headerForegroundcolor1 );
        headerlastnameLabel	 .setForeground( headerForegroundcolor1 );
        headerpositionLabel    .setForeground( headerForegroundcolor1 );
        headerdnameLabel       .setForeground( headerForegroundcolor1);

        Color headerBackgroundcolor1 = new Color(203, 67, 30) ;

        headerindexLabel       .setBackground( headerBackgroundcolor1 );
        headereidLabel	     .setBackground( headerBackgroundcolor1 );
        headerfirstnameLabel	 .setBackground( headerBackgroundcolor1 );
        headerlastnameLabel	 .setBackground( headerBackgroundcolor1 );
        headerpositionLabel    .setBackground( headerBackgroundcolor1 );
        headerdnameLabel       .setBackground( headerBackgroundcolor1);
        
        dataPanel.add(headerindexLabel);
        dataPanel.add(headereidLabel);	
        dataPanel.add(headerfirstnameLabel);
        dataPanel.add(headerlastnameLabel);
        dataPanel.add(headerpositionLabel);
        dataPanel.add(headerdnameLabel);

        int  i = 1 ; 
		
		while(itr.hasNext()){
            
            employee emp = itr.next() ; 

            JTextField indexLabel = new JTextField("  " + String.valueOf(i));
            JTextField eidLabel = new JTextField(String.valueOf(emp.EID));
            JTextField firstnameLabel = new JTextField(emp.FirstName);
            JTextField lastnameLabel = new JTextField(emp.LastName);
            JTextField positionLabel = new JTextField(emp.Position);
            JTextField dnameLabel = new JTextField(emp.Dname) ;

            indexLabel.setEditable(false);
            eidLabel.setEditable(false);
            firstnameLabel.setEditable(false);
            lastnameLabel.setEditable(false);
            positionLabel.setEditable(false);
            dnameLabel.setEditable(false);
	
            indexLabel       .setFont(new Font("",Font.BOLD,20));
			eidLabel	     .setFont(new Font("",Font.BOLD,20));
			firstnameLabel	 .setFont(new Font("",Font.BOLD,20));
			lastnameLabel	 .setFont(new Font("",Font.BOLD,20));
			positionLabel    .setFont(new Font("",Font.BOLD,20));
			dnameLabel       .setFont(new Font("",Font.BOLD,20));
			
            
			Color setForegroundcolor2 = new Color(8, 22, 31 ) ;

			indexLabel       .setForeground(setForegroundcolor2);
			eidLabel	     .setForeground(setForegroundcolor2);
			firstnameLabel	 .setForeground(setForegroundcolor2);
			lastnameLabel	 .setForeground(setForegroundcolor2);
			positionLabel    .setForeground(setForegroundcolor2);
			dnameLabel       .setForeground(setForegroundcolor2);

            Color headerBackgroundcolor2 = null ;
            if(i%2==0){
                headerBackgroundcolor2 = new Color(44, 134, 193 ) ;
            }else{
                headerBackgroundcolor2 = new Color(30, 103, 151 ) ;
            }
          

            indexLabel       .setBackground( headerBackgroundcolor2 );
            eidLabel	     .setBackground( headerBackgroundcolor2 );
            firstnameLabel	 .setBackground( headerBackgroundcolor2 );
            lastnameLabel	 .setBackground( headerBackgroundcolor2 );
            positionLabel    .setBackground( headerBackgroundcolor2 );
            dnameLabel       .setBackground( headerBackgroundcolor2 );
			
            dataPanel.add(indexLabel);
			dataPanel.add(eidLabel);	
			dataPanel.add(firstnameLabel);
			dataPanel.add(lastnameLabel);
			dataPanel.add(positionLabel);
			dataPanel.add(dnameLabel);

            i++;
         }

        
                

                
                
                
            }});

        



    }
























    public void studentmenu(String studentId){
        
        student s  = database.get_student_data(studentId);  
        
        

        panel.removeAll();
		panel.updateUI();

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(new ImageIcon("src\\background32.jpg").getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT)));
        background.setBounds(0, 0, Width, Height);
        panel.add(background);

        Border blackline = BorderFactory.createLineBorder(Color.black,5);
      

        JTextField er_TextField = new JTextField(" Notifications will be displayed Here ");
		er_TextField.setForeground(Color.ORANGE);
		er_TextField.setBackground(Color.BLACK);
		er_TextField.setFont(new Font("",Font.PLAIN,18));
		er_TextField.setBounds(1230 , 30 , 340 , 50);
        background.add(er_TextField);
        
        JLabel student_label = new JLabel( s.StudentId + " : " + 
        s.FirstName + "  " + s.LastName + 
        " : Department of " + s.Dname + " : " + s.CID ) ;

		student_label.setForeground(Color.BLACK);
		student_label.setBackground(Color.BLACK);
		student_label.setFont(new Font("",Font.BOLD,18));
		student_label.setBounds(80 , 20 , 500 , 100);
        background.add(student_label);

        JButton signout_Button = new JButton(" Sign Out ");
		signout_Button.setFont(new Font("",Font.PLAIN,20));
		signout_Button.setBackground(Color.ORANGE);
        signout_Button.setBounds(1300, 750, 200, 50);
        background.add(signout_Button);

        signout_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
 
                panel.removeAll();
                panel.updateUI();
                mainmenu();
                
            }});

        //Get supervisor id // make a clearance record with student entity and the supervisor entity
        // check status 

        JButton clearance_Button = new JButton(" Apply For Clearance ");
		clearance_Button.setFont(new Font("",Font.PLAIN,20));
        clearance_Button.setBorder(blackline);
		clearance_Button.setBackground(new Color(55, 89, 203));
        clearance_Button.setForeground(new Color(97, 41, 17));
        clearance_Button.setBounds(600, 280, 400, 50);
        background.add(clearance_Button);

        clearance_Button.addActionListener(new ActionListener() {
				
                public void actionPerformed(ActionEvent e) {
                    
                    int i = database.apply_for_clearance(s.StudentId , s.DID , 1);
                    if(i==0){
                        er_TextField.setText("Successfully Applied");
                    }else if(i==1){
                        er_TextField.setText("Already Applied");
                    }else if(i==2){
                        er_TextField.setText("Clearance Process not Started");
                    }
                    
                }});

        ////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////

        JButton checkStatus_Button = new JButton(" Check Clearance Status");
		checkStatus_Button.setFont(new Font("",Font.PLAIN,20));
		checkStatus_Button.setBackground(new Color(55, 89, 203));
        checkStatus_Button.setForeground(new Color(97, 41, 17));
        checkStatus_Button.setBorder(blackline);
        checkStatus_Button.setBounds(600, 360, 400, 50);
        background.add(checkStatus_Button);
        
        checkStatus_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {

        //////////////////////////////////////////////////////////////////

        signout_Button.setVisible(false);
        checkStatus_Button.setVisible(false);
        clearance_Button.setVisible(false);

        JButton go_back_Button = new JButton(" Go Back ");
		go_back_Button.setFont(new Font("",Font.PLAIN,20));
		go_back_Button.setBackground(Color.ORANGE);
        go_back_Button.setBounds(1300, 750, 200, 50);
        background.add(go_back_Button);

        go_back_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
 
                panel.removeAll();
                panel.updateUI();
                studentmenu(studentId);
                
            }});


        //////////////////////////////////////////////////////////////

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(Color.BLACK);
        dataPanel.setVisible(true);
               
        JScrollPane scrolldataPanel = new JScrollPane(dataPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        scrolldataPanel.setBounds(320 , 200, 960 , 420);  
        
        scrolldataPanel.setVisible(true);
        
        background.add(scrolldataPanel);
                
        ArrayList<clearance1> clearanceData = database.get_clearance_status_1(s.StudentId);
        Iterator<clearance1> itr = clearanceData.iterator();

        int rows =  clearanceData.size(); 
        dataPanel.setLayout(new GridLayout( rows+1 , 7 , 5 , 5));
        dataPanel.setBorder(blackline);

                
        JTextField headerindexLabel = new JTextField(" Index ");
        JTextField headereidLabel = new JTextField(" EID ");
        JTextField headerfirstnameLabel = new JTextField("First Name");
        JTextField headerlastnameLabel = new JTextField("Second Name");
        JTextField headerpositionLabel = new JTextField("Position");
        JTextField headerdstatusLabel = new JTextField("  Status  ") ; 
        JTextField headerdCommentLabel = new JTextField(" Comment ") ; 

        headerindexLabel.setEditable(false);
        headereidLabel.setEditable(false);
        headerfirstnameLabel.setEditable(false);
        headerlastnameLabel.setEditable(false);
        headerpositionLabel.setEditable(false);
        headerdstatusLabel.setEditable(false);
        headerdCommentLabel.setEditable(false);

        
        headerindexLabel       .setFont(new Font("",Font.BOLD,20));
        headereidLabel	     .setFont(new Font("",Font.BOLD,20));
        headerfirstnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerlastnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerpositionLabel    .setFont(new Font("",Font.BOLD,20));
        headerdstatusLabel      .setFont(new Font("",Font.BOLD,20));
        headerdCommentLabel.setFont(new Font("",Font.BOLD,20));

        Color headercolor1 = new Color(8, 22, 31 ) ;

        headerindexLabel       .setForeground( headercolor1 );
        headereidLabel	     .setForeground( headercolor1 );
        headerfirstnameLabel	 .setForeground( headercolor1 );
        headerlastnameLabel	 .setForeground( headercolor1 );
        headerpositionLabel    .setForeground( headercolor1 );
        headerdstatusLabel       .setForeground( headercolor1);
        headerdCommentLabel.setForeground( headercolor1);

        Color headerBackgroundcolor1 = new Color(203, 67, 30) ;

        headerindexLabel.setBackground( headerBackgroundcolor1 );
        headereidLabel.setBackground( headerBackgroundcolor1 );
        headerfirstnameLabel.setBackground( headerBackgroundcolor1 );
        headerlastnameLabel.setBackground( headerBackgroundcolor1 );
        headerpositionLabel.setBackground( headerBackgroundcolor1 );
        headerdstatusLabel.setBackground( headerBackgroundcolor1 );
        headerdCommentLabel.setBackground( headerBackgroundcolor1 );


        dataPanel.add(headerindexLabel);
        dataPanel.add(headereidLabel);	
        dataPanel.add(headerfirstnameLabel);
        dataPanel.add(headerlastnameLabel);
        dataPanel.add(headerpositionLabel);
        dataPanel.add(headerdstatusLabel);
        dataPanel.add(headerdCommentLabel);

        int  i = 1 ; 
		
		while(itr.hasNext()){
            
            clearance1 c = itr.next() ; 

            JTextField indexLabel = new JTextField("  " + String.valueOf(i));
            JTextField eidLabel = new JTextField(String.valueOf(c.EID));
            JTextField firstnameLabel = new JTextField(c.employee_FirstName);
            JTextField lastnameLabel = new JTextField(c.employee_SecondName);
            JTextField positionLabel = new JTextField(c.employee_Position);

            JTextField statusLabel = new JTextField() ;
            int x =  Integer.valueOf(c.ClearanceStatus) ; 
            
            if(x==1){
                statusLabel.setText("  Approved  " );
            }else{
                statusLabel.setText(" Not Approved ");
            }

            JTextField CommentLabel = new JTextField(c.Comment) ;

            indexLabel.setEditable(false);
            eidLabel.setEditable(false);
            firstnameLabel.setEditable(false);
            lastnameLabel.setEditable(false);
            positionLabel.setEditable(false);
            statusLabel.setEditable(false);
            CommentLabel.setEditable(false);

            indexLabel       .setFont(new Font("",Font.BOLD,20));
			eidLabel	     .setFont(new Font("",Font.BOLD,20));
			firstnameLabel	 .setFont(new Font("",Font.BOLD,20));
			lastnameLabel	 .setFont(new Font("",Font.BOLD,20));
			positionLabel    .setFont(new Font("",Font.BOLD,20));
			statusLabel      .setFont(new Font("",Font.BOLD,20));
			CommentLabel     .setFont(new Font("",Font.BOLD,20));


			Color color1 = new Color(8, 22, 31 ) ;

			indexLabel       .setForeground(color1);
			eidLabel	     .setForeground(color1);
			firstnameLabel	 .setForeground(color1);
			lastnameLabel	 .setForeground(color1);
			positionLabel    .setForeground(color1);
			statusLabel       .setForeground(color1);
            CommentLabel .setForeground(color1);

            
            Color headerBackgroundcolor2 = null ;
            if(i%2==0){
                headerBackgroundcolor2 = new Color(44, 134, 193 ) ;
            }else{
                headerBackgroundcolor2 = new Color(30, 103, 151 ) ;
            }

            indexLabel .setBackground( headerBackgroundcolor2 );
            eidLabel .setBackground( headerBackgroundcolor2 );
            firstnameLabel .setBackground( headerBackgroundcolor2 );
            lastnameLabel .setBackground( headerBackgroundcolor2 );
            positionLabel .setBackground( headerBackgroundcolor2 );
            statusLabel .setBackground( headerBackgroundcolor2 );
            CommentLabel .setBackground( headerBackgroundcolor2 );
			
			
            dataPanel.add(indexLabel);
			dataPanel.add(eidLabel);	
			dataPanel.add(firstnameLabel);
			dataPanel.add(lastnameLabel);
			dataPanel.add(positionLabel);
			dataPanel.add(statusLabel);
            dataPanel.add(CommentLabel);

            i++;
         }

         

            }});

    }





































    public void facultymenu(String EmployeeId){

        panel.removeAll();
		panel.updateUI();
        panel.setLayout(null);

        panel.setBackground(Color.GRAY);

        JLabel background = new JLabel(); 
        background.setBounds(0,0,Width,Height);
        background.setIcon(new ImageIcon(new ImageIcon("src\\background12.jpg").getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT)));
        panel.add(background);

        JButton signout_Button = new JButton(" Sign Out ");
		signout_Button.setFont(new Font("",Font.PLAIN,20));
		signout_Button.setBackground(Color.ORANGE);
        signout_Button.setBounds(1300, 750, 200, 50);
        background.add(signout_Button);

        signout_Button.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {
                
                panel.removeAll();
                panel.updateUI();
                mainmenu();
                
            }});

        ///////////////////////////////////////////////////////////////////

        JTextField er_TextField = new JTextField(" Notifications will be displayed Here ");
		er_TextField.setForeground(Color.ORANGE);
		er_TextField.setBackground(Color.BLACK);
		er_TextField.setFont(new Font("",Font.PLAIN,18));
		er_TextField.setBounds(1230 , 30 , 340 , 50);
        background.add(er_TextField);

        ///////////////////////////////////////////////////////////////////

        Border blackline = BorderFactory.createLineBorder(Color.black,5);


        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(1230, 100, 340, 500);
        actionPanel.setBackground(new Color(12, 89, 130));
        actionPanel.setLayout(null);
        actionPanel.setBorder(blackline);
        actionPanel.setVisible(true);
        background.add(actionPanel);

        ///////////////////////////////////////////////////////////////////

        TitledBorder std_id__Title = BorderFactory.createTitledBorder ("Enter Student ID");
        std_id__Title.setTitleColor(new Color(251, 120, 8 ));
        std_id__Title.setBorder(null);
        std_id__Title.setTitleJustification(TitledBorder.CENTER);
        std_id__Title.setTitlePosition(TitledBorder.TOP);
    

        JTextField std_id_TextField  = new JTextField();
		std_id_TextField.setForeground(Color.ORANGE);
		std_id_TextField.setBackground(new Color(1, 1, 31 ));
        std_id_TextField.setHorizontalAlignment(JTextField.CENTER);
		std_id_TextField.setBorder(std_id__Title);
        std_id_TextField.setFont(new Font("",Font.PLAIN,18));
		std_id_TextField.setBounds(30 , 50 , 280 , 60);
        actionPanel.add(std_id_TextField);

        String s1[] = {" Not Aprroved" , " Approved "};
        JComboBox approval_combobox = new JComboBox(s1);
        approval_combobox.setForeground(Color.ORANGE);
		approval_combobox.setBackground(Color.BLACK);
		approval_combobox.setFont(new Font("",Font.PLAIN,18));
		approval_combobox.setBounds(30 , 160 , 280 , 60);
        approval_combobox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        actionPanel.add(approval_combobox);

        TitledBorder comment__Title = BorderFactory.createTitledBorder ("Add Comment");
        comment__Title.setTitleColor(new Color(251, 120, 8 ));
        comment__Title.setBorder(null);
        comment__Title.setTitleJustification(TitledBorder.CENTER);
        comment__Title.setTitlePosition(TitledBorder.TOP);

        JTextField comment_TextField  = new JTextField();
		comment_TextField.setForeground(Color.ORANGE);
		comment_TextField.setBackground(new Color(1, 1, 31 ));
        comment_TextField.setBorder(comment__Title);
        comment_TextField.setHorizontalAlignment(JTextField.CENTER);
		comment_TextField.setFont(new Font("",Font.PLAIN,18));
		comment_TextField.setBounds(30 , 270 , 280 , 60);
        actionPanel.add(comment_TextField);

        JButton update_clearificationButton = new JButton(" Update ");
		update_clearificationButton .setFont(new Font("",Font.PLAIN,20));
		update_clearificationButton .setBackground(Color.ORANGE);
        update_clearificationButton .setBounds(30, 380, 280, 60);
        actionPanel.add(update_clearificationButton);

        update_clearificationButton.addActionListener(new ActionListener() {
				
            public void actionPerformed(ActionEvent e) {

                String StudentId = std_id_TextField.getText();
                String comment   = comment_TextField.getText().trim();

                try {
                    comment = comment.substring(0, 8);
                } catch (Exception e1) {
                    comment = comment ;  
                }
               

                int    status    = approval_combobox.getSelectedIndex();
                
                int i = database.update_clearance(EmployeeId , StudentId , status , comment);

                if(i==1){
                    er_TextField.setText("Id not Found");
                }else if ( i == 0 ){
                    er_TextField.setText("Successfull");
                    facultymenu(EmployeeId);
                }else if( i == 1)
                    er_TextField.setText("Error");


            }});
        
        

















        
        // clearance data for employee based on student
        ///////////////////////////////////////////////////////////////////
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(Color.BLACK);
        dataPanel.setVisible(true);
       

        JScrollPane scrolldataPanel = new JScrollPane(dataPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        scrolldataPanel.setBounds(50, 100, 1150, 500);

        scrolldataPanel.setVisible(true);
       

        background.add(scrolldataPanel);

        dataPanel.removeAll();
        dataPanel.updateUI();
                
        ArrayList<clearance2> clearanceData = database.get_clearance_status_2(EmployeeId);
        Iterator<clearance2> itr = clearanceData.iterator();

        int rows =  clearanceData.size(); 
        dataPanel.setLayout(new GridLayout( rows+1 , 8 , 5 ,5));
        dataPanel.setBorder(blackline);
    
                
        JTextField headerindexLabel = new JTextField(" Index ");
        JTextField headereidLabel = new JTextField(" EID ");
        JTextField headerfirstnameLabel = new JTextField("First Name");
        JTextField headerlastnameLabel = new JTextField("Second Name");
        JTextField headerrankLabel = new JTextField(" Rank ");
        JTextField headercidLabel = new JTextField(" Course ");
        JTextField headerdstatusLabel = new JTextField("  Status  ") ; 
        JTextField headerdCommentLabel = new JTextField(" Comment ") ; 


        headerindexLabel.setEditable(false);
        headercidLabel.setEditable(false);
        headerfirstnameLabel.setEditable(false);
        headerlastnameLabel.setEditable(false);
        headerrankLabel.setEditable(false);
        headercidLabel.setEditable(false);
        headerdstatusLabel.setEditable(false);
        headerdCommentLabel.setEditable(false);



        headerindexLabel       .setFont(new Font("",Font.BOLD,20));
        headereidLabel	     .setFont(new Font("",Font.BOLD,20));
        headerfirstnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerlastnameLabel	 .setFont(new Font("",Font.BOLD,20));
        headerrankLabel    .setFont(new Font("",Font.BOLD,20));
        headercidLabel .setFont(new Font("",Font.BOLD,20));
        headerdstatusLabel      .setFont(new Font("",Font.BOLD,20));
        headerdCommentLabel.setFont(new Font("",Font.BOLD,20));

        Color headercolor1 = new Color(8, 22, 31) ;

        headerindexLabel       .setForeground( headercolor1 );
        headereidLabel	     .setForeground( headercolor1 );
        headerfirstnameLabel	 .setForeground( headercolor1 );
        headerlastnameLabel	 .setForeground( headercolor1 );
        headerrankLabel    .setForeground( headercolor1 );
        headercidLabel .setForeground( headercolor1 );
        headerdstatusLabel       .setForeground( headercolor1);
        headerdCommentLabel.setForeground( headercolor1);

        Color headerBackgroundcolor1 = new Color(203, 67, 30) ;

        headerindexLabel.setBackground( headerBackgroundcolor1 );
        headercidLabel.setBackground( headerBackgroundcolor1 );
        headerfirstnameLabel.setBackground( headerBackgroundcolor1 );
        headerlastnameLabel.setBackground( headerBackgroundcolor1 );
        headerrankLabel.setBackground( headerBackgroundcolor1 );
        headereidLabel.setBackground( headerBackgroundcolor1 );
        headerdstatusLabel.setBackground( headerBackgroundcolor1 );
        headerdCommentLabel.setBackground( headerBackgroundcolor1 );

        dataPanel.add(headerindexLabel);
        dataPanel.add(headereidLabel);	
        dataPanel.add(headerfirstnameLabel);
        dataPanel.add(headerlastnameLabel);
        dataPanel.add(headerrankLabel);
        dataPanel.add(headercidLabel);
        dataPanel.add(headerdstatusLabel);
        dataPanel.add(headerdCommentLabel);

        int  i = 1 ; 
		
		while(itr.hasNext()){
            
            clearance2 c = itr.next() ; 

            JTextField indexLabel = new JTextField("  " + String.valueOf(i));
            JTextField eidLabel = new JTextField(String.valueOf(c.studentId));
            JTextField firstnameLabel = new JTextField(c.student_FirstName);
            JTextField lastnameLabel = new JTextField(c.student_lastName);
            JTextField rankLabel = new JTextField(c.student_rank);
            JTextField cidLabel = new JTextField(c.student_CID);

            JTextField statusLabel = new JTextField() ;
            int x =  Integer.valueOf(c.ClearanceStatus) ; 
            
            if(x==1){
                statusLabel.setText("  Approved  " );
            }else{
                statusLabel.setText(" Not Approved ");
            }

            JTextField CommentLabel = new JTextField(c.Comment) ;

            

            indexLabel.setEditable(false);
            eidLabel.setEditable(false);
            firstnameLabel.setEditable(false);
            lastnameLabel.setEditable(false);
            rankLabel.setEditable(false);
            cidLabel.setEditable(false);
            statusLabel.setEditable(false);
            CommentLabel.setEditable(false);

            indexLabel       .setFont(new Font("",Font.BOLD,20));
			eidLabel	     .setFont(new Font("",Font.BOLD,20));
			firstnameLabel	 .setFont(new Font("",Font.BOLD,20));
			lastnameLabel	 .setFont(new Font("",Font.BOLD,20));
			rankLabel    .setFont(new Font("",Font.BOLD,20));
            cidLabel .setFont(new Font("",Font.BOLD,20));
			statusLabel       .setFont(new Font("",Font.BOLD,20));
			CommentLabel.setFont(new Font("",Font.BOLD,20));


			Color color1 = new Color(8, 22, 31 ) ;

			indexLabel       .setForeground(color1);
			eidLabel	     .setForeground(color1);
			firstnameLabel	 .setForeground(color1);
			lastnameLabel	 .setForeground(color1);
			rankLabel    .setForeground(color1);
            cidLabel.setForeground(color1);
			statusLabel       .setForeground(color1);
            CommentLabel .setForeground(color1);

            Color headerBackgroundcolor2 = null ;
            if(i%2==0){
                headerBackgroundcolor2 = new Color(44, 134, 193 ) ;
            }else{
                headerBackgroundcolor2 = new Color(30, 103, 151 ) ;
            }

            indexLabel .setBackground( headerBackgroundcolor2 );
            eidLabel .setBackground( headerBackgroundcolor2 );
            firstnameLabel .setBackground( headerBackgroundcolor2 );
            lastnameLabel .setBackground( headerBackgroundcolor2 );
            rankLabel .setBackground( headerBackgroundcolor2 );
            cidLabel .setBackground( headerBackgroundcolor2 );
            statusLabel .setBackground( headerBackgroundcolor2 );
            CommentLabel .setBackground( headerBackgroundcolor2 );
			
            dataPanel.add(indexLabel);
			dataPanel.add(eidLabel);	
			dataPanel.add(firstnameLabel);
			dataPanel.add(lastnameLabel);
			dataPanel.add(rankLabel);
            dataPanel.add(cidLabel);
			dataPanel.add(statusLabel);
            dataPanel.add(CommentLabel);

            i++;
         }
          
    
        
    }

}
