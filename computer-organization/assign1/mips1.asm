.data
buffer: .space 2
resCap: .asciiz "Alpha\n", "Bravo\n", "China\n", "Delta\n", "Echo\n", "Foxtrot\n", "Golf\n", "Hotel\n", "India\n", "Juliet\n", "Kilo\n", "Lima\n", "Mary\n", "November\n", "Oscar\n", "Paper\n", "Quebec\n", "Research\n", "Sierra\n", "Tango\n", "Uniform\n", "Victor\n", "Whisky\n", "X-ray\n", "Yankee\n", "Zulu\n"
resLow: .asciiz "alpha\n", "bravo\n", "china\n", "delta\n", "echo\n", "foxtrot\n", "golf\n", "hotel\n", "india\n", "juliet\n", "kilo\n", "lima\n", "mary\n", "november\n", "oscar\n", "paper\n", "quebec\n", "research\n", "sierra\n", "tango\n", "uniform\n", "victor\n", "whisky\n", "x-ray\n", "yankee\n", "zulu\n"
resLetIndex: .word 0, 7, 14, 21, 28, 34, 43, 49, 56, 63, 71, 77, 83, 89, 99, 106, 113, 121, 131, 139, 146, 155, 163, 171, 178, 186 
resNum: .asciiz "zero\n", "First\n", "Second\n", "Third\n", "Fourth\n", "Fifth\n", "Sixth\n", "Seventh\n", "Eighth\n", "Ninth\n"
resNumIndex: .word 0, 6, 13, 21, 28, 36, 43, 50, 59, 67
question: .byte '?'
star: .asciiz "*"

.text
.globl main
main:	li $v0, 8
	la $a0, buffer
	li $a1, 2
	syscall
	
	lb $a2, question
	lb $a3, ($a0)
	beq $a2, $a3, end
	
	li $a2, '0'
	bge $a3, $a2, num
	
back:	la $a0, star
	li $v0, 4
	syscall
	
	j main
	
num:	li $a2, 'A'
	bge $a3, $a2, cap	
	
	li $a2, '9'
	bgt $a3, $a2, back
	
	li $a2, '0'
	sub $s0, $a3, $a2
	li $s1, 4
	mult $s0, $s1
	mflo $s0
	la $s1, resNumIndex
	add $s2, $s1, $s0
	lw $s3, ($s2)
	la $s4, resNum
	add $a0, $s4, $s3
	li $v0, 4
	syscall
	
	j main
			
cap:	li $a2, 'a'
	bge $a3, $a2, low
	
	li $a2, 'Z'
	bgt $a3, $a2, back
	
	li $a2, 'A'
	sub $s0, $a3, $a2
	li $s1, 4
	mult $s0, $s1
	mflo $s0
	la $s1, resLetIndex
	add $s2, $s1, $s0
	lw $s3, ($s2)
	la $s4, resCap
	add $a0, $s4, $s3
	li $v0, 4
	syscall
	
	j main
	
low:	li $a2, 'z'
	bgt $a3, $a2, back
	
	li $a2, 'a'
	sub $s0, $a3, $a2
	li $s1, 4
	mult $s0, $s1
	mflo $s0
	la $s1, resLetIndex
	add $s2, $s1, $s0
	lw $s3, ($s2)
	la $s4, resLow
	add $a0, $s4, $s3
	li $v0, 4
	syscall
	
	j main
	
end: 	li $v0, 10
	syscall	

	
