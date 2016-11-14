<?php

if(!isset($_GET['gclid'])) exit("No gclid param");

$gclid = $_GET['gclid'];
function gclid_decode($gclid){
	$binData = str_split(base64_decode(str_replace(array('_','-'), array('+','/'), $gclid)));
	
	if(ord($binData[0]) != 8) return -1;
	
	$timeDigits = array();
	$counter = 0;
	for($i = 0;$i < count($binData)-1;$i++){
		$digit = ord($binData[$i + 1]);
		if($digit == 21){
			$counter = $i;
			break;
		}
		if($i <= 6)
			$timeDigits[$i] = $digit - 128;
		else
			$timeDigits[$i] = $digit;
	}
	$timeData = "0";
	
	for ($i = 0; $i < $counter; $i++) {
		$tmp1 = bcpow("128",$i);
		$tmp4 = bcmul($tmp1, $timeDigits[$i]);
		$timeData = bcadd($timeData,$tmp4);
		
		
	}
	
	if (!preg_match("/024100$/", $timeData))
		return -1;
	
	$timestamp = substr($timeData,0, strlen($timeData) - 6);
	
	return intval($timestamp);
}
$timestamp = gclid_decode($gclid);
if($timestamp == -1)  exit("Invalid code");
echo $timestamp."<br>";
echo date('l jS \of F Y h:i:s A', $timestamp);

?>