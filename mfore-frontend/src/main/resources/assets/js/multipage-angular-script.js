angular
.module('mnyrsApp', [])
.controller(
		'MainCtrl',
		[
		 '$http', '$scope', '$filter',
		 function($http, $scope, $filter) {
			 var self = this;
			 
			 // variable initialized from from http get
			 var ang_person;
			 var ang_per_con_det;
			 var ang_mnyrs_spc_det;
			 var ang_med_rem_serv = [];
			 var ang_reading_type = [];

			 var time = new Date();
			$scope.checked_day = [];
			var tempData = [];
			 self.dayExist = function(day) {
				 if(!(angular.isUndefined(self.ang_reading_type))){
					  for (var i=0; i<self.ang_reading_type.length; i++){
						  if(day === self.ang_reading_type[i].reading_days){
				        	   return true;
						  }
					  }	
				 }
				 
			};

			// Notice the type: value is not enclosed with single quote. 
			// if ther server sends an integer then it should not enclosed 
			// in order to map correctly
			 self.ang_social_id_type = [
							           {name:'Passport', type:0},
							           {name:'Driving License', type:1},
							           {name:'Kela', type:2},
							           {name:'Other', type:3},
							           {name:'National ID', type:4},
							           {name:'Ration Stamp', type:5}
							         ];
		
			//self.ang_social_id_type = ['Passport', 'Driving License', 'Kela', 'Other', 'National ID', 'Ration Stamp'];

			/*self.chkbxs = [{label:"Led Zeppelin", val:false },{label:"Electric Light Orchestra", val:false },{label:"Mark Almond", val:false }];
			$scope.$watch( "chkbxs" , function(n,o){
				console.log('hello');
		        var trues = $filter("filter")( n , {val:true} );
		        $scope.flag = $scope.trues.length;
		    }, true );*/
				 
				 
			 //self.ang_blood_type = ['A', 'B', 'AB', 'A+', 'B+', 'O', 'O-', 'O+'];
			 self.ang_blood_type = [
					           {name:'A', type:'0'},
					           {name:'B', type:'1'},
					           {name:'AB', type:'2'},
					           {name:'A+', type:'3'},
					           {name:'O', type:'4'},
					           {name:'O+', type:'5'},
					           {name:'O-', type:'6'}
					         ];

			 // This is dummy initialization, the real data will replace it from webservice call later
			 self.ang_reading_type = [
			              {
			            	  id:'',
			            	  reading_type:'',
			              },
			              {
			            	  id:'',
			            	  reading_type:'',
			              }];
			 //self.ang_countries = ['Bangladesh', 'India', 'Japan', 'Norway', 'Pakistan', 'Finkland'];
			 self.ang_countries = [
			                       {name:'Bangladesh', id:'0'},
			                       {name:'India', id:'1'},
			                       {name:'Japan', id:'2'},
			                       {name:'Norway', id:'3'},
			                       {name:'Finland', id:'4'},
			                       {name:'United Kingdom', id:'5'}
			                       ];
			 
			 self.colors = [
			           {name:'black', shade:'dark'},
			           {name:'white', shade:'light'},
			           {name:'red', shade:'dark'},
			           {name:'blue', shade:'dark'},
			           {name:'yellow', shade:'light'}
			         ];
			 //self.ang_reading_type = ['Fasting Blood Glucose Level', 'Random Blood Glucose Level'];
			 self.ang_reading_type_serv = [								            
			                   {
			                	   type: '1',
			                	   value: 'Fasting Blood Glucose Level',
			                   },
			                   {
			                	   type: '2',
			                	   value: 'Random Blood Glucose Level'
			                   }];
			 self.days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
			 
			 $scope.checked_days = tempData;
			 
			 // Need an empty arry for the checkList directive to work properly
			 
			 /*self.days = [
			              { label: 'Monday', val 'False'},{ label: 'Tuesday', val 'False'},
			              { label: 'Wednesday', val 'False'},{ label: 'Thursday', val 'False'},
			              { label: 'Friday', val 'False'},{ label: 'Saturday', val 'False'},
			              { label: 'Sunday', val 'False'}];*/

			 self.medicines = [								            
			                   {
			                	   id: 1,
			                	   name: 'amiloride',
			                   },
			                   {
			                	   id: 2,
			                	   name: 'triamterene',
			                   },
			                   {
			                	   id: 3,
			                	   name: 'bumetanide',
			                   }];
			 

			 $http
			 .get(
			 '/mfore/registration/prefillperson')
			 .then(
					 function(response) {
						 self.ang_person = response.data;
						 console
						 .log(response.data);
					 },
					 function(errResponse) {
						 console
						 .error('Error while fetching notes');
					 });
			 $http
			 .get(
			 '/mfore/registration/prefillpersoncontactdetail')
			 .then(
					 function(response) {
						 self.ang_per_con_det = response.data;
						 console
						 .log(response.data);
					 },
					 function(errResponse) {
						 console
						 .error('Error while fetching notes');
					 });
			 $http
			 .get(
			 '/mfore/registration/prefillmnyrsspecificdetail')
			 .then(
					 function(response) {
						 self.ang_mnyrs_spc_det = response.data;
						 console
						 .log(response.data);
					 },
					 function(errResponse) {
						 console
						 .error('Error while fetching notes');
					 });
			 $http
			 .get(
			 '/mfore/registration/prefillmedicationreminder')
			 .then(
					 function(response) {
						 self.ang_med_rem_serv = response.data;
						  console
						 .log(response.data);
					 },
					 function(errResponse) {
						 console
						 .error('Error while fetching notes');
					 });
			 $http
			 .get(
			 '/mfore/registration/prefillreadingtype')
			 .then(
					 function(response) {
						 self.ang_reading_type = response.data;
						 for(var i=0;i<response.data.length;i++){
							 tempData.push(response.data[i].reading_days);
						 }
						 console.log(response.data);
						 console
						 .log(response.data);
					 },
					 function(errResponse) {
						 console
						 .error('Error while fetching notes');
					 });
		 } ]).directive('checkList', function() {
			 return {
				 scope: {
					 list: '=checkList',
					 value: '@'
				 },
				 link: function(scope, elem, attrs) {
					 var handler = function(setup) {
						 var checked = elem.prop('checked');
						 var index = scope.list.indexOf(scope.value);

						 if (checked && index == -1) {
							 if (setup) elem.prop('checked', false);
							 else{ scope.list.push(scope.value);
							 console.log('scope.value'+scope.value);
							 $("input:radio").show();
							 }
						 } 
						 else if (!checked && index != -1) 
						 {
							 if (setup) {
								 elem.prop('checked', true);
								 }
							 else 
							 {	scope.list.splice(index, 1);
							 	var tempValue = scope.list ;
							 	console.log('tempValue'+tempValue +index);
							 	if(tempValue == 0){
								 //alert('There should be a selection atleast');
							 		console.log('check this fuck');
							 		$("input:radio").hide();	
							 	}
							 }
						 }
					 };

					 var setupHandler = handler.bind(null, true);
					 var changeHandler = handler.bind(null, false);

					 elem.on('change', function() {
						 scope.$apply(changeHandler);
					 });
					 scope.$watch('list', setupHandler);
				 }
			 };
		 });

