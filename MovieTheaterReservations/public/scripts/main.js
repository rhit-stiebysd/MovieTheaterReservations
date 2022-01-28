/* @author 
 * Sam Stieby, Ryan Nikolic, Hayden Mattick
 */


var rhit = rhit || {};

rhit.LoginPageController = class {
	constructor() {
		//Enable onclick listeners
		document.querySelector("#submitLoginButton").onclick = (event) => {
			// this.login();
			window.location.href = "/home.html";
		}
		this.checkForRedirect();
	}

	updateView() {

	}
};

rhit.HomePageController = class {
	constructor() {
		document.querySelector("#viewEmployeeData").onclick = (event) => {
			fetch(`/api/getemployeedata/${userid}`)
				.then(response => response.json())
				.then(data => {
					console.log(data);
				});
		}
		document.querySelector("#viewTheaterList").onclick = (event) => {
			fetch(`/api/getmovietheaters/`)
			.then(response => response.json())
			.then(data => {
				console.log(data);
			});
		}
		document.querySelector("#viewReservations").onclick = (event) => {
			fetch(`/api/getreservations/${userid}`)
			.then(response => response.json())
			.then(data => {
				console.log(data);
			});
		}
	}
}

rhit.main = function () {
	new rhit.LoginPageController();
	new rhit.HomePageController();
};

rhit.main();
