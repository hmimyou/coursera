.data 
buffer1: .space 500
buffer2: .space 2
successInfo: .asciiz "Success! Location: \0"
failInfo: .asciiz "Fail!\n"
return: .asciiz "\n"
.text
.globl main
main:	li $v0, 8
	la $a0, buffer1
	li $a1, 500
	syscall
	
	
		
loop1:	
	la $a0, buffer2
	li $a1, 2
	li $v0, 8
	syscall
	
	li $a2, '?'
	lb $a3, ($a0)
	beq $a2, $a3, end
	
	la $a0, return
	li $v0, 4
	syscall
	
	li $s0, 1
	la $s1, buffer1
loop2:	
	lb $v1, ($s1)
	li $t0, '\n'
	beq $v1, $t0, miss
	
	li $t0, '\t'
	beq $v1, $t0, miss
	
	li $t0, '\0'
	beq $v1, $t0, miss
	
	beq $a3, $v1, found

	addi $s2, $s0, 1
	move $s0, $s2
	
	addi $s2, $s1, 1
	move $s1, $s2	
	
	j loop2	

found:	
	la $a0, successInfo
	li $v0, 4
	syscall 
	
	move $a0, $s0
	li $v0, 1
	syscall
	
	la $a0, return
	li $v0, 4
	syscall
	
	j loop1

miss:	
	la $a0, failInfo
	li $v0, 4
	syscall 
	
	j loop1

end: 	li $v0, 10
	syscall	

	
