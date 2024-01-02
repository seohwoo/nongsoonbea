<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>차트</title>
	</head>
	<body>
		<center><h1>${catename}</h1></center>
		<div>
		  <canvas id="myChart" style="width: 100%; height: 500px"></canvas>
		</div>
		
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		
		<script>
		  const ctx = document.getElementById('myChart');
		
		  new Chart(ctx, {
		    type: 'bar',
		    data: {
		      labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		      datasets: [{
		        label: '${lastYear}',
		        data: ${lastlist},
		        borderWidth: 1
		      },
		      {
		          label: '${thisYear}',
		          data: ${thislist},
		          borderWidth: 1
		        }
		      
		      ]
		    },
		    options: {
		      scales: {
		        y: {
		          beginAtZero: true
		        }
		      }
		    }
		  });
		</script>
	</body>
</html>