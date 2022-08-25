export default{
	template : `
<!--		 <div class="pricing-header p-3 pb-md-4 mx-auto text-center">
			<h1 class="display-4 fw-normal">서울특별시 강남구 논현동</h1>
		</div>-->
		<div style="width: 1200px; height: 450px; display: flex;">
        
	 	<!--차트가 그려질 부분-->
        <canvas id="myChart" width="900"></canvas>
	 
	 	<table class="table" style="margin-left: 30px;">
			<thead class="thead-light">
				<tr class="text-center">
					<th>시간</th>
					<th>날씨</th>
				</tr>
			</thead>
			<tbody id="sky">
			<!--
				<tr>
					<td></td>
					<td></td>
				</tr>
			-->
			</tbody>
		</table>
    </div>
	`,
	created() {

    },
	methods : {

	}
}